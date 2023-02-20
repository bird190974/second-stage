package com.bird.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {
    @Id
    private String regSign;
    private String model;
    @Enumerated(EnumType.STRING)
    private Engine engineCategory;
    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;
    private String color;
    private int seatsQuantity;
    private BigDecimal costPerDay;
    private String image;
}
