package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import com.texnoera.socialmedia.service.abstracts.PostImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-images")
public class PostImageController {

    private final PostImageService postImageService;

    @PostMapping("/upload")
    public ResponseEntity<PostImageResponse> upload(@RequestParam("image") MultipartFile file, @RequestParam Integer postId) throws IOException {
        log.info("Received request to upload image for postId={}", postId);
        log.info("Uploading image with filename={} for postId={} with size={} bytes",
                file.getOriginalFilename(), postId, file.getSize());
        PostImageResponse response = postImageService.upload(file, postId);
        log.info("Successfully uploaded image for postId={}", postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{postId}")
    public ResponseEntity<byte[]> download(@PathVariable Integer postId) {
        log.info("Received request to download image for postId={}", postId);
        byte[] image = postImageService.download(postId);
        log.info("Image retrieval status for postId={} - {}", postId, (image != null ? "Found" : "Not Found"));
        return image != null ?
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
