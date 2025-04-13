package com.texnoera.socialmedia.model.response.postImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImageResponse {

    private Integer id;
    private String name;
    private String type;
    private String imageUrl;
    private Integer postId;
}
