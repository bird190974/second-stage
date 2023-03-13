package com.bird.dao;

import com.bird.dto.OrderFilter;
import com.bird.entity.OrderStatus;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderRepositoryIT extends TestBase {
    @Test
    void save() {

        var order = TestUtil.getOrder(TestUtil.getUser(), TestUtil.getCar());
        var orderRepository = new OrderRepository(session);

        var actualOrder = orderRepository.save(order);

        assertThat(actualOrder).isNotNull();
    }

    @Test
    void update() {
        var orderRepository = new OrderRepository(session);

        var expectedOrder = orderRepository.findById(1L).get();
        expectedOrder.setStatus(OrderStatus.DENIED);
        orderRepository.update(expectedOrder);

        var actualOrder = orderRepository.findById(1L);

        assertThat(actualOrder).isNotNull();
        assertThat(actualOrder.get().getStatus()).isEqualTo(OrderStatus.DENIED);
    }

    @Test
    void deleteExistingOrder() {
        var orderRepository = new OrderRepository(session);

        orderRepository.delete(1L);

        assertThat(orderRepository.findById(1L)).isEmpty();
    }
    @Test
    void deleteNotExistingOrder() {
        var orderRepository = new OrderRepository(session);

        assertThrows(IllegalArgumentException.class, () -> orderRepository.delete(100500100L));
    }
    @Test
    void findById() {
        var orderRepository = new OrderRepository(session);

        var actualOrder = orderRepository.findById(1L);

        assertThat(actualOrder).isPresent();
        assertThat(actualOrder.get().getUser().getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void findAll() {
        var orderRepository = new OrderRepository(session);

        var actualOrder = orderRepository.findAll();

        assertNotNull(actualOrder);
        assertThat(actualOrder).hasSize(3);
    }

    @Test
    void findByFilterWithOneParam() {
        var orderRepository = new OrderRepository(session);

        var orderFilter = OrderFilter.builder()
                .status(OrderStatus.DENIED)
                .build();
        var orders = orderRepository.findByFilter(orderFilter);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getUser().getFirstName()).isEqualTo("Sveta");
    }


    @Test
    void findByFilterWithNoParams() {
        var orderRepository = new OrderRepository(session);

        var orderFilter = OrderFilter.builder()
                .build();
        var orders = orderRepository.findByFilter(orderFilter);

        assertThat(orders).hasSize(orderRepository.findAll().size());

    }


}