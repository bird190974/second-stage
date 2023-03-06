package com.bird.dto;

import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {

    Gearbox gearbox;
    Engine engine;
    Integer seatsQuantity;
}
