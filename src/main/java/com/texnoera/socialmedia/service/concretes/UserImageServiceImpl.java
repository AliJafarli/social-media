package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import com.texnoera.socialmedia.service.abstracts.UserImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserImageServiceImpl implements UserImageService {
    @Override
    public UserImageResponse upload(MultipartFile file, Long userId) {
        return null;
    }

    @Override
    public byte[] download(Long Id) {
        return new byte[0];
    }
}
