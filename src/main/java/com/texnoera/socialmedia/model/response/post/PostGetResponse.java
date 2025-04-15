package com.texnoera.socialmedia.model.response.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostGetResponse {

    private Integer id;
    private String content;
    private int likesCount;
    private LocalDateTime created;
    private String createdBy;
}
