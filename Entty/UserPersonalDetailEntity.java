package com.car.dekho.car.dekho.Entty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_personal_details")
public class UserPersonalDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @Column(name = "age",nullable = false)
    private Integer age;
    @Column(name = "mobile_no",nullable = false)
    private Long mobileNo;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "country",nullable = false)
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
