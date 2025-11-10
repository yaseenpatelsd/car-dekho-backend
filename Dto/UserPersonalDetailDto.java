package com.car.dekho.car.dekho.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonalDetailDto {
    private String fullName;
    private Integer age;
    private Long mobileNo;
    private String address;
    private String city;
    private String state;
    private String country;
}
