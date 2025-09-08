package com.texnoera.socialmedia.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(max = 30)
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 50)
    private String password;
}
