package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {


    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    PostGetResponse postToGetResponse(Post post);

    @Mapping(source = "userId", target = "user.id")
    Post postAddRequestToPost(PostAddRequest postAddRequest);
    List<PostGetResponse> postsToGetResponses(List<Post> posts);

}
