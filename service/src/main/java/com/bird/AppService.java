package com.bird;

import com.bird.entity.*;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;

public class AppService {
    public static void main(String[] args) {
        System.out.println("Hello world! This is main method from service module. ");
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
             Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            Car car = Car.builder()
                    .regSign("aaa2245")
                    .model("C4 Picasso")
                    .seatsQuantity(5)
                    .engineCategory(Engine.DIESEL)
                    .gearbox(Gearbox.MANUAL)
                    .color("White")
                    .costPerDay(BigDecimal.valueOf(15.44))
                    .build();
            User user = User.builder()
                    .firstName("Siarhei")
                    .lastName("Lebedzeu")
                    .email("data7479@mail.ru")
                    .password("")
                    .userRole(Role.USER)
                    .build();

            session.saveOrUpdate(user);
            session.saveOrUpdate(car);

            session.getTransaction().commit();
        }
    }
}