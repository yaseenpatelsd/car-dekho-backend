package com.car.dekho.car.dekho.Entty;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_entity")
@Builder
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "brand",nullable = false)
    private String brand;
    @Column(name = "modelNo",nullable = false)
    private String model;
    @Column(name = "modelYear",nullable = false)
    private String year;
    @Column(name = "price",nullable = false)
    private double price;
    @Column(name = "post_by", nullable = false)
    private String postBy;
    @Column(name = "contact_no",nullable = false)
    private Long mobileNo;
}
