package com.car.dekho.car.dekho.Entty;

import com.car.dekho.car.dekho.Dto.UserPersonalDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offer_entity")
@Builder
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount",nullable = false)
    private Double price;
    @Column(name = "note")
    private String note;
    @Column(name = "name",nullable = false)
    private String offererName;
    @Column(name = "city",nullable = false)
    private String city;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_entity")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_personal_details_id")
    private UserPersonalDetailEntity userPersonalDetails;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CarEntity car;
}
