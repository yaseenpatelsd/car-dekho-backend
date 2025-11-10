package com.car.dekho.car.dekho.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerificationDto {
     @NotBlank(message = "Username is compulsory")
    private String username;
     @NotBlank(message = "otp require")
     @Size(min = 5 , max = 5 ,message = "please enter valid 5 digit otp")
    private String otp;
}
