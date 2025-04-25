package com.texnoera.socialmedia.model.response.userImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImageResponse {

    private Integer id;
    private String name;
    private String type;
    private String imageUrl;
    private Integer userId;

}
