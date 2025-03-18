package com.texnoera.socialmedia.model.response.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    private Long id;
    private Long userId;
    private String username;
}
