package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.UserImageMapper;
import com.texnoera.socialmedia.model.entity.UserImage;
import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import com.texnoera.socialmedia.repository.UserImageRepository;
import com.texnoera.socialmedia.service.abstracts.UserImageService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;
    private final UserImageMapper userImageMapper;
    private final UserService userService;
    
    @Override
    public UserImageResponse upload(MultipartFile file, Long userId) {
        return null;
    }

    @Override
    public byte[] download(Long Id) {
        return new byte[0];
    }
}
