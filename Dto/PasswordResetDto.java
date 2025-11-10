package com.car.dekho.car.dekho.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public class PasswordResetDto {
        @NotBlank(message = "username is required")
        private String username;
        @NotBlank(message = "otp is required")
        private String otp;
        @NotBlank(message = "new password is required")
        @Size(min = 6 ,max = 15,message = "Strong password is needed")
        private String password;
    }

