package com.texnoera.socialmedia.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max=30)
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max=80)
    @Column(name = "password", nullable = false, length = 80)
    private String password;

}
