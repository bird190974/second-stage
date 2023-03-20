package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bird.entity.QCar.car;

@Repository
public class CarRepository extends BaseRepository<Integer, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

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
