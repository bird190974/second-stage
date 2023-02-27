package com.bird.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "orders")
@Builder
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @Builder.Default
    @OneToMany(mappedBy = "car")
    private List<Order> orders = new ArrayList<>();
}
