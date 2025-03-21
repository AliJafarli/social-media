package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import com.texnoera.socialmedia.service.abstracts.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    @Override
    public PostImageResponse upload(MultipartFile file, Long postId) {
        return null;
    }

    @Override
    public byte[] download(Long Id) {
        return new byte[0];
    }
}
