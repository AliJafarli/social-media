package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.UserImageMapper;
import com.texnoera.socialmedia.model.entity.UserImage;
import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import com.texnoera.socialmedia.model.result.ImageNotFoundException;
import com.texnoera.socialmedia.repository.UserImageRepository;
import com.texnoera.socialmedia.service.abstracts.UserImageService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import com.texnoera.socialmedia.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService {

    private final UserImageRepository userImageRepository;
    private final UserImageMapper userImageMapper;
    private final UserService userService;

    @Override
    public UserImageResponse upload(MultipartFile file, Long userId) throws IOException {
        UserImage userImage = new UserImage();
        userImage.setData(ImageUtil.compressImage(file.getBytes()));
        userImage.setName(file.getOriginalFilename());
        userImage.setType(file.getContentType());
        userImage.setUser(userService.getById(userId));
        userImageRepository.save(userImage);
        return userImageMapper.userImageToResponse(userImage);
    }

    @Override
    public byte[] download(Long id) {
        Optional<UserImage> userImage = userImageRepository.findByUser_Id(id);
        if (userImage.isPresent()) {
            return ImageUtil.decompressImage(userImage.get().getData());
        } else {
            throw new ImageNotFoundException("No image found for user with id " + id);
        }

    }
}
