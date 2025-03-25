package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserImageService {

    UserImageResponse upload(MultipartFile file, Long userId) throws IOException;
    byte[] download(Long id);
}
