package com.bird.testUtils;

import com.bird.entity.Car;
import com.bird.entity.Client;
import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import com.bird.entity.Order;
import com.bird.entity.OrderStatus;
import com.bird.entity.Role;
import com.bird.entity.User;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestUtil {

    public static Car getCar() {
        return Car.builder()
                .regSign("5054 IT-2")
                .model("Skoda Kodiaq")
                .engineCategory(Engine.GAS)
                .gearbox(Gearbox.ROBOT)
                .color("Green")
                .seatsQuantity(5)
                .costPerDay(BigDecimal.valueOf(22.22))
                .image("")
                .build();
    }

    public static User getUser(){
        return User.builder()
                .firstName("Siarhei")
                .lastName("Lebedzeu")
                .email("data@gmail.com")
                .password("1234")
                .userRole(Role.USER)
                .build();
    }

    public static Client getClient(User user){
        return Client.builder()
                .user(user)
                .passportId("12N34")
                .driverLicenceNo("1234BY")
                .driverLicenceExpiration(LocalDate.of(2023,12,31))
                .creditAmount(BigDecimal.valueOf(100.00))
                .clientRating(5)
                .build();
    }

    public static Order getOrder(User user, Car car){
        return Order.builder()
                .user(user)
                .car(car)
                .beginTime(LocalDateTime.of(2023,2,24, 12, 30))
                .endTime(LocalDateTime.of(2023, 2, 26, 12, 0))
                .status(OrderStatus.ACCEPTED)
                .message("Nice to see you, " + user.getFirstName())
                .build();
    }
}
