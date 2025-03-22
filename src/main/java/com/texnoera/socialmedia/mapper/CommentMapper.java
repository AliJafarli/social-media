package com.texnoera.socialmedia.mapper;

import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.username", target = "username")
    CommentGetResponse commentToResponse(Comment comment);

    List<CommentGetResponse> commentsToResponses(List<Comment> comments);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "post.id")
    Comment addRequestToComment(CommentAddRequest commentAddRequest);

}
