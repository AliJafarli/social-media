package com.texnoera.socialmedia.model.response.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {

    private Long id;
    private Long userId;
    private Long postId;
    private String username;
    private String commentText;
}
