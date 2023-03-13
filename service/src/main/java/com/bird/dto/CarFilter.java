package com.bird.dto;

import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CarFilter {
    String model;
    Engine engine;
    Gearbox gearbox;
    String color;
    Integer seatsQuantity;
    BigDecimal costPerDay;
}
