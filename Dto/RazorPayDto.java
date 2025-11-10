package com.car.dekho.car.dekho.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorPayDto {
        private String id;
        private String amount;
        private String currency;
        private String status;

}
