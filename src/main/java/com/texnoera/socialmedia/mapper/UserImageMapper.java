package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.UserImage;
import com.texnoera.socialmedia.model.response.userImage.UserImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserImageMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "imageUrl", expression = "java(com.texnoera.socialmedia.utils.ImageUtil.buildUserImageUrl(userImage.getId()))")
    UserImageResponse userImageToResponse(UserImage userImage);
}
