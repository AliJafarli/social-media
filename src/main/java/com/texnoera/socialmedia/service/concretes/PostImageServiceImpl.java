package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.PostImageMapper;
import com.texnoera.socialmedia.model.entity.PostImage;
import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import com.texnoera.socialmedia.repository.PostImageRepository;
import com.texnoera.socialmedia.service.abstracts.PostImageService;
import com.texnoera.socialmedia.service.abstracts.PostService;
import com.texnoera.socialmedia.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostImageMapper postImageMapper;
    private final PostService postService;

    @Override
    public PostImageResponse upload(MultipartFile file, Integer postId) throws IOException {
        PostImage postImage = new PostImage();
        postImage.setName(file.getOriginalFilename());
        postImage.setType(file.getContentType());
        postImage.setData(ImageUtil.compressImage(file.getBytes()));
        postImage.setPost(postService.getById(postId));
        postImageRepository.save(postImage);
        return postImageMapper.postImageToResponse(postImage);
    }

    @Override
    public byte[] download(Integer id) {
        Optional<PostImage> postImage = postImageRepository.findPostImageByPost_Id(id);
        return postImage.map(image -> ImageUtil.
                decompressImage(image.getData())).orElse(null);
    }
}
