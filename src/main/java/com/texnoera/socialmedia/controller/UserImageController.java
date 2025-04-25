package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import com.texnoera.socialmedia.service.abstracts.UserImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/v1/user-images")
public class UserImageController {

    private final UserImageService userImageService;

    @PostMapping
    public ResponseEntity<UserImageResponse> upload(@RequestParam("image") MultipartFile file,
                                                    @RequestParam Integer userId) throws IOException {
        log.info("Received request to upload image for userId={}", userId);
        log.info("Uploading file: {} for userId={} with size={} bytes",
                file.getOriginalFilename(), userId, file.getSize());
        UserImageResponse response = userImageService.upload(file, userId);
        log.info("Successfully uploaded file for userId={}", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> download(@PathVariable Integer userId) {
        log.info("Received request to download image for userId={}", userId);
        byte[] image = userImageService.download(userId);
        log.info("Image retrieval status for userId={} - {}", userId, (image != null ? "Found" : "Not Found"));
        return image != null ?
                ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable Integer id) {
        log.info("Received request to view image for userId={}", id);
        byte[] imageData = userImageService.downloadUserImage(id);
        String contentType = userImageService.getContentTypeById(id);

        if (imageData == null) {
            log.warn("Image not found for userId={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));

        log.info("Successfully retrieved image for userId={}", id);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


}
