package com.car.dekho.car.dekho.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponseDto {
    @NotNull(message = "please enter valid offer price")
    @Size(min = 10000 ,message = "please enter a valid price")
    private Double price;
    private String note;
    @NotBlank(message = "offerer name is compulsory")
    private String offererName;
    @NotBlank(message = "please enter valid city")
    private String city;
}