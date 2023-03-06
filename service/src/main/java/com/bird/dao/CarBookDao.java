package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Car;
import com.bird.entity.Car_;
import com.bird.entity.Order;
import com.bird.entity.Order_;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static com.bird.entity.QCar.*;
import static com.bird.entity.QOrder.*;
import static com.bird.entity.QUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarBookDao {

    private static final CarBookDao INSTANCE = new CarBookDao();
    private static final String GRAPH_NAME = "WithOrders";

    public static CarBookDao getInstance() {
        return INSTANCE;
    }

    public List<Order> findAllByFilterQueryDsl(Session session, CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getGearbox(), car.gearbox::eq)
                .add(filter.getEngine(), car.engineCategory::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return new JPAQuery<Car>(session)
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.car, car)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph(GRAPH_NAME))
                .fetch();
    }

    public List<Order> findAllByFilterCriteria(Session session, CarFilter filter) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(Order.class);
        var order = criteria.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getGearbox() != null) {
            predicates.add(cb.equal(order.get(Order_.CAR).get(Car_.GEARBOX), filter.getGearbox()));
        }
        if (filter.getEngine() != null) {
            predicates.add(cb.equal(order.get(Order_.CAR).get(Car_.ENGINE_CATEGORY), filter.getEngine()));
        }
        if (filter.getSeatsQuantity() != null) {
            predicates.add(cb.equal(order.get(Order_.CAR).get(Car_.SEATS_QUANTITY), filter.getSeatsQuantity()));
        }

        criteria.select(order).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph(GRAPH_NAME))
                .list();
    }
}
