package com.bird.testUtils;

import com.bird.entity.Car;
import com.bird.entity.Client;
import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import com.bird.entity.Order;
import com.bird.entity.OrderStatus;
import com.bird.entity.Role;
import com.bird.entity.User;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass

public class TestDataCreate {

    public void createData(SessionFactory sessionFactory) {
        @Cleanup var session = sessionFactory.openSession();

        Car audi = saveCar(session, "1122 IX-2", "Audi Q3", Engine.GAS, Gearbox.AUTOMATIC, "Yellow", 5, BigDecimal.valueOf(10.44), "NoImage");
        Car tesla = saveCar(session, "1123 IX-2", "Tesla S3", Engine.ELECTRIC, Gearbox.AUTOMATIC, "Black", 5, BigDecimal.valueOf(13.44), "NoImage");
        Car skoda = saveCar(session, "1124 IX-2", "Skoda Kodiaq", Engine.DIESEL, Gearbox.ROBOT, "Green", 7, BigDecimal.valueOf(8.44), "NoImage");

        User ivan = saveUser(session, "Ivan", "Ivanov", "ivan@gmail.com", "1111", Role.USER);
        User petr = saveUser(session, "Petr", "Petrov", "petr@gmail.com", "2222", Role.USER);
        User sveta = saveUser(session, "Sveta", "Svetikova", "sveta@gmail.com", "3333", Role.USER);

        Client ivanIvanov = saveClient(session, ivan, "123I456", "I123456", LocalDate.of(2023, 12, 31), BigDecimal.valueOf(120.45), 5);
        Client petrPetov = saveClient(session, petr, "123P456", "P123456", LocalDate.of(2023, 6, 30), BigDecimal.valueOf(255.55), 10);
        Client svetaSvetikova = saveClient(session, sveta, "123S456", "S123456", LocalDate.of(2023, 1, 10), BigDecimal.valueOf(111.11), 1);

        Order order1 = saveOrder(session, ivan, audi, LocalDateTime.now(), LocalDateTime.now().plusDays(10), OrderStatus.ACCEPTED, "Nice to see you, " + ivan.getFirstName());
        Order order2 = saveOrder(session, petr, tesla, LocalDateTime.now(), LocalDateTime.now().plusDays(5), OrderStatus.IN_PROGRESS, "Please wait, " + petr.getFirstName());
        Order order3 = saveOrder(session, sveta, skoda, LocalDateTime.now(), LocalDateTime.now().plusDays(20), OrderStatus.DENIED, "Sorry, " + sveta.getFirstName());
    }

    private Car saveCar(Session session, String regSign, String model, Engine engine, Gearbox gearbox, String color,
                        int seatsQuantity, BigDecimal costPerDay, String image) {
        Car car = Car.builder()
                .regSign(regSign)
                .model(model)
                .engineCategory(engine)
                .gearbox(gearbox)
                .color(color)
                .seatsQuantity(seatsQuantity)
                .costPerDay(costPerDay)
                .image(image)
                .build();

        session.save(car);
        return car;
    }

    private User saveUser(Session session, String firstName, String lastName, String email, String password, Role role) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .build();

        session.save(user);
        return user;
    }

    private Client saveClient(Session session, User user, String passportNo, String driverLicenceNo, LocalDate driverLicenceExpiration, BigDecimal creditAmount, int clientRating) {
        Client client = Client.builder()
                .user(user)
                .passportNo(passportNo)
                .driverLicenceNo(driverLicenceNo)
                .driverLicenceExpiration(driverLicenceExpiration)
                .creditAmount(creditAmount)
                .clientRating(clientRating)
                .build();

        session.save(client);

        return client;
    }

    private Order saveOrder(Session session, User user, Car car, LocalDateTime beginTime, LocalDateTime endTime, OrderStatus status, String message) {
        Order order = Order.builder()
                .user(user)
                .car(car)
                .beginTime(beginTime)
                .endTime(endTime)
                .status(status)
                .message(message)
                .build();

        session.save(order);
        return order;
    }

}
