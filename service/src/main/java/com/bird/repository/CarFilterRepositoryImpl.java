package com.bird.repository;

import com.bird.dto.CarFilter;
import com.bird.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QCar.car;

@AllArgsConstructor
public class CarFilterRepositoryImpl implements CarFilterRepository {

    private final EntityManager entityManager;

    @Override
    public List<Car> findByFilter(CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getGearbox(), car.gearbox::eq)
                .add(filter.getEngine(), car.engineCategory::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();
        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}
