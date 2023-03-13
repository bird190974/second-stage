package com.bird.dao;

import com.bird.dto.OrderFilter;
import com.bird.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QCar.car;
import static com.bird.entity.QOrder.order;
import static com.bird.entity.QUser.user;

public class OrderRepository extends BaseRepository<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findByFilter(OrderFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getUser(), order.user::eq)
                .add(filter.getCar(), order.car::eq)
                .add(filter.getBeginTime(), order.beginTime::eq)
                .add(filter.getEndTime(), order.endTime::eq)
                .add(filter.getStatus(), order.status::eq)
                .buildAnd();

        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.car, car)
                .where(predicate)
                .fetch();
    }
}
