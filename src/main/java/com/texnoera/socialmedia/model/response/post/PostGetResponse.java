package com.texnoera.socialmedia.model.response.post;

import com.texnoera.socialmedia.model.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostGetResponse {

    private Integer id;
    private String content;
    private Set<Like> likes;
    private LocalDateTime created;
    private String createdBy;
}
