package com.texnoera.socialmedia.model.response.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {

    private Integer id;
    private Integer userId;
    private Integer postId;
    private String username;
    private String commentText;
}
