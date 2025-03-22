package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.PostImage;
import com.texnoera.socialmedia.model.response.postImage.PostImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostImageMapper {

    @Mapping(source = "post.id",target = "postId")
    PostImageResponse postImageToResponse(PostImage postImage);


}
