package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostImageService {

    PostImageResponse upload(MultipartFile file, Integer postId) throws IOException;
    byte[] download(Integer id);

}
