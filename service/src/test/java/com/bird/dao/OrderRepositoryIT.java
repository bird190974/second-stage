package com.bird.dao;

import com.bird.dto.OrderFilter;
import com.bird.entity.OrderStatus;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class OrderRepositoryIT extends TestBase {

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        var order = TestUtil.getOrder(TestUtil.getUser(), TestUtil.getCar());

        var actualOrder = orderRepository.save(order);

        assertThat(actualOrder.getId()).isNotNull();
    }

    @Test
    void update() {
        var expectedOrder = orderRepository.findById(1L).get();
        expectedOrder.setStatus(OrderStatus.DENIED);
        orderRepository.update(expectedOrder);
        entityManager.clear();

        var actualOrder = orderRepository.findById(1L);

        assertThat(actualOrder).isPresent();
        assertThat(actualOrder.get().getStatus()).isEqualTo(OrderStatus.DENIED);
    }

    @Test
    void delete() {
        var order = TestUtil.getOrder(TestUtil.getUser(), TestUtil.getCar());

        orderRepository.save(order);
        orderRepository.delete(order);
        entityManager.clear();

        var maybeOrder = orderRepository.findById(order.getId());
        assertThat(maybeOrder).isEmpty();
    }

    @Test
    void findById() {
        var actualOrder = orderRepository.findById(1L);
        entityManager.clear();

        assertThat(actualOrder).isPresent();
        assertThat(actualOrder.get().getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    void findAll() {
        var actualOrder = orderRepository.findAll();
        entityManager.clear();

        assertNotNull(actualOrder);
        assertThat(actualOrder).hasSize(3);
    }

    @Test
    void findByFilterWithOneParam() {
        var orderFilter = OrderFilter.builder()
                .status(OrderStatus.DENIED)
                .build();

        var orders = orderRepository.findByFilter(orderFilter);

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getUser().getFirstName()).isEqualTo("Sveta");
    }


    @Test
    void findByFilterWithNoParams() {
        var orderFilter = OrderFilter.builder()
                .build();

        var orders = orderRepository.findByFilter(orderFilter);

        assertThat(orders).hasSize(orderRepository.findAll().size());
    }
}