package com.bird.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class Order {
    @Id
    private Long id;
    private Integer userId;
    private Integer carId;
    private LocalDate beginTime;
    private LocalDate endTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String message;



}
