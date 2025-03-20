package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {

    PostImageResponse upload(MultipartFile file, Long postId);
    byte[] download(Long Id);

}
