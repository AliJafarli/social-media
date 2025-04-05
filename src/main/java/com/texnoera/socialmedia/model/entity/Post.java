package com.texnoera.socialmedia.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 1, max = 1000)
    @Column(name = "content", nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<PostImage> postImages;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
