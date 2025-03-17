package com.texnoera.socialmedia.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max=30)
    @Column(name = "username", nullable = false,unique = true, length = 30)
    private String username;

    @Size(max=50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max=50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Size(max=80)
    @Column(name = "password", nullable = false, length = 80)
    private String password;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Follow> following;

    @OneToMany(mappedBy = "following",cascade = CascadeType.ALL)
    private Set<Follow> followers;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserImage> images;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Comment> comments;

}
