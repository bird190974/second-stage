package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarRepositoryIT extends TestBase {

    private final CarRepository carRepository = context.getBean(CarRepository.class);

    @Test
    void save() {
        var car = TestUtil.getCar();

        var actualCar = carRepository.save(car);

        assertThat(actualCar.getId()).isNotNull();
    }

    @Test
    void update() {
        var expectedCar = carRepository.findById(1).get();
        expectedCar.setModel("Audi Q7");
        carRepository.update(expectedCar);
        session.clear();

        var maybeCar = carRepository.findById(1);

        assertThat(maybeCar).isPresent();
        assertThat(maybeCar.get().getModel()).isEqualTo("Audi Q7");
    }

    @Test
    void delete() {
        var car = TestUtil.getCar();

        carRepository.save(car);
        carRepository.delete(car);
        session.clear();

        var maybeCar = carRepository.findById(car.getId());

        assertThat(maybeCar).isEmpty();
    }

    @Test
    void findById() {
        var actualCar = carRepository.findById(1);
        session.clear();

        assertThat(actualCar).isPresent();
        assertThat(actualCar.get().getModel()).isEqualTo("Audi Q3");
    }

    @Test
    void findAll() {
        var actualCars = carRepository.findAll();
        session.clear();

        assertNotNull(actualCars);
        assertThat(actualCars).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
        var carFilter = CarFilter.builder()
                .engine(Engine.DIESEL)
                .gearbox(Gearbox.ROBOT)
                .seatsQuantity(7)
                .build();

        var cars = carRepository.findByFilter(carFilter);

        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getModel()).isEqualTo("Skoda Kodiaq");
    }

    @Test
    void findByFilterWithTwoParams() {
        var carFilter = CarFilter.builder()
                .gearbox(Gearbox.AUTOMATIC)
                .seatsQuantity(5)
                .build();
        var cars = carRepository.findByFilter(carFilter);

        assertThat(cars).hasSize(2);
        assertThat(cars.get(0).getModel()).isEqualTo("Audi Q3");
        assertThat(cars.get(1).getModel()).isEqualTo("Tesla S3");
    }

    @Test
    void findByFilterWithNoParams() {
        var carFilter = CarFilter.builder()
                .build();
        var cars = carRepository.findByFilter(carFilter);

        assertThat(cars).hasSize(carRepository.findAll().size());
    }
}