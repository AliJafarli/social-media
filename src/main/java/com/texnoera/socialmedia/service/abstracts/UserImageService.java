package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {

    UserImageResponse upload(MultipartFile file, Long userId);
    byte[] download(Long Id);
}
