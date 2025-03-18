package com.texnoera.socialmedia.model.response.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}

