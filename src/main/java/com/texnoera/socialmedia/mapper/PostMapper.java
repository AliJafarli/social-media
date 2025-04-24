package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.PostImage;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "imageUrls", expression = "java(mapImageUrls(post))")
    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    PostGetResponse postToGetResponse(Post post);

    @Mapping(source = "userId", target = "user.id")
    Post postAddRequestToPost(PostAddRequest postAddRequest);
    List<PostGetResponse> postsToGetResponses(List<Post> posts);

    default List<String> mapImageUrls(Post post) {
        return post.getPostImages() == null ? List.of() :
                post.getPostImages().stream()
                        .map(image -> "/api/v1/post-images/view/" + image.getId())
                        .toList();
    }
}
