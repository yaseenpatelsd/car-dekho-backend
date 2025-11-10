package com.car.dekho.car.dekho.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    @NotBlank(message = "required")
    private String brand;
    @NotBlank(message = "required")
    private String fullName;
    @NotBlank(message = "required")
    private String model;
    @NotBlank(message = "required")
    private String year;
    @NotNull(message = "required")
    private Double price;

}
