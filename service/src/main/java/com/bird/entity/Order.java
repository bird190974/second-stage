package com.bird.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NamedEntityGraph(name = "WithOrders",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("car")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(exclude = {"user", "car"})
@ToString(exclude = {"user", "car"})
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String message;

    public void setUser(User user) {
        this.user = user;
        this.user.getOrders().add(this);
    }

    public void setCar(Car car) {
        this.car = car;
        this.car.getOrders().add(this);
    }
}
