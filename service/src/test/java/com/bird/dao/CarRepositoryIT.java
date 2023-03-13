package com.bird.dao;

import com.bird.dto.CarFilter;
import com.bird.entity.Engine;
import com.bird.entity.Gearbox;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarRepositoryIT extends TestBase {

    @Test
    void save() {
        var car = TestUtil.getCar();
        var carRepository = new CarRepository(session);

        var actualCar = carRepository.save(car);

        assertThat(actualCar).isNotNull();
    }

    @Test
    void update() {
        var carRepository = new CarRepository(session);

        var expectedCar = carRepository.findById(1).get();
        expectedCar.setModel("Audi Q7");
        carRepository.update(expectedCar);

        var actualCar = carRepository.findById(1);

        assertThat(actualCar).isNotNull();
        assertThat(actualCar.get().getModel()).isEqualTo("Audi Q7");
    }

    @Test
    void deleteExistingCar() {
        var carRepository = new CarRepository(session);

        carRepository.delete(1);

        assertThat(carRepository.findById(1)).isEmpty();
    }
    @Test
    void deleteNotExistingCar() {
        var carRepository = new CarRepository(session);

        assertThrows(IllegalArgumentException.class, () -> carRepository.delete(60000));
    }
    @Test
    void findById() {
        var carRepository = new CarRepository(session);

        var actualCar = carRepository.findById(1);

        assertThat(actualCar).isPresent();
        assertThat(actualCar.get().getModel()).isEqualTo("Audi Q3");
    }

    @Test
    void findAll() {
        var carRepository = new CarRepository(session);

        var actualCars = carRepository.findAll();

        assertNotNull(actualCars);
        assertThat(actualCars).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
        var carRepository = new CarRepository(session);

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
        var carRepository = new CarRepository(session);

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
        var carRepository = new CarRepository(session);

        var carFilter = CarFilter.builder()
                .build();
        var cars = carRepository.findByFilter(carFilter);

        assertThat(cars).hasSize(carRepository.findAll().size());

    }


}