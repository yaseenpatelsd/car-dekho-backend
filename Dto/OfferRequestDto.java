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
public class OfferRequestDto {
    @NotNull(message = "car id is require to sand offer")
    private Long carId;
    @Size(min = 10000 ,message = "please enter a valid price")
    private Double price;
    private String note;
}
