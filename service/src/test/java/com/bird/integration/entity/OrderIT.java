package com.bird.integration.entity;

import com.bird.entity.Car;
import com.bird.entity.Client;
import com.bird.entity.Order;
import com.bird.entity.OrderStatus;
import com.bird.entity.User;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIT extends TestBase {
    @Test
    void saveOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);
        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.flush();
        session.clear();

        Order expectedOrder = session.get(Order.class, order.getId());
        assertThat(expectedOrder).isNotNull();
    }

    @Test
    void getOrderById() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);
        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.flush();
        session.clear();

        Order expectedOrder = session.get(Order.class, order.getId());

        assertThat(expectedOrder).isEqualTo(order);
    }


    @Test
    void updateOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);
        OrderStatus updatedOrderStatus = OrderStatus.DENIED;
        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.flush();

        order.setStatus(updatedOrderStatus);
        session.update(order);
        session.flush();
        session.clear();
        Order updatedOrder = session.get(Order.class, order.getId());

        assertThat(updatedOrder.getStatus()).isEqualTo(updatedOrderStatus);
    }

    @Test
    void deleteOrder() {
        User user = TestUtil.getUser();
        Car car = TestUtil.getCar();
        Client client = TestUtil.getClient(user);
        Order order = TestUtil.getOrder(user, car);
        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.flush();

        session.delete(order);
        session.flush();
        session.clear();
        Order deletedOrder = session.get(Order.class, order.getId());

        assertThat(deletedOrder).isNull();
    }
}


