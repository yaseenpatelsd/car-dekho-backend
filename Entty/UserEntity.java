package com.car.dekho.car.dekho.Entty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    @Column(name = "role",nullable = false)
    private String role;

    @Column(name = "Subscriber?")
    private String subscriber="nonSubscriber";


    private String otp;
    private Long otpExpire;
    private Boolean isValid=false;
}
