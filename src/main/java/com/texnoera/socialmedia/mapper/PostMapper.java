package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "imageUrls", expression = "java(mapImageUrls(post))")
    @Mapping(target = "likesCount", expression = "java(post.getLikes() != null ? post.getLikes().size() : 0)")
    PostGetResponse postToGetResponse(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "postImages", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post postAddRequestToPost(PostAddRequest postAddRequest);
    List<PostGetResponse> postsToGetResponses(List<Post> posts);

    default List<String> mapImageUrls(Post post) {
        return post.getPostImages() == null ? List.of() :
                post.getPostImages().stream()
                        .map(image -> "/api/v1/post-images/view/" + image.getId())
                        .toList();
    }
}
