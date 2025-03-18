package com.texnoera.socialmedia.model.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostGetResponse {

    private Long id;
    private Long userId;
    private String username;
    private String content;
}
