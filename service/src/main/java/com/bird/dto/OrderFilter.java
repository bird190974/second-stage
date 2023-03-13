package com.bird.dto;

import com.bird.entity.Car;
import com.bird.entity.OrderStatus;
import com.bird.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class OrderFilter {
    User user;
    Car car;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    OrderStatus status;
}
