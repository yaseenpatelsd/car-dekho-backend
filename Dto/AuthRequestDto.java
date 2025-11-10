package com.car.dekho.car.dekho.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "please enter valid username")
    private String username;
    @NotBlank(message = "please enter valid password")
    private String password;
    @NotBlank(message = "please enter valid email")
    @Email(message = "email should be valid")
    private String email;

}
