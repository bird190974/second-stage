package com.bird.integration.entity;

import com.bird.entity.Car;
import com.bird.entity.Client;
import com.bird.entity.Order;
import com.bird.entity.OrderStatus;
import com.bird.entity.User;
import com.bird.testUtils.TestUtil;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @Test
    void saveAndGetOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            session.beginTransaction();

            session.save(user);
            session.save(client);
            session.save(car);
            session.save(order);
            session.flush();
            session.evict(order);

            Order createdOrder = session.get(Order.class, order.getId());
            assertThat(createdOrder.getMessage()).isEqualTo(order.getMessage());

            session.getTransaction().commit();
        }
    }


    @Test
    void updateOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);
        OrderStatus updatedOrderStatus = OrderStatus.DENIED;

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            session.beginTransaction();

            session.save(user);
            session.save(client);
            session.save(car);
            session.save(order);

            order.setStatus(updatedOrderStatus);
            session.update(order);
            session.flush();
            session.evict(order);
            Order updatedOrder = session.get(Order.class, order.getId());
            assertThat(updatedOrder.getStatus()).isEqualTo(updatedOrderStatus);

            session.getTransaction().commit();
        }
    }

    @Test
    void deleteOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.save(client);
            session.save(car);
            session.save(order);

            session.delete(order);
            session.flush();
            session.evict(order);
            Order deletedOrder = session.get(Order.class, order.getId());

            assertThat(deletedOrder).isNull();

            session.getTransaction().commit();
        }
    }
}


