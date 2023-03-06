package com.bird.integration.entity;

import com.bird.entity.Car;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarIT extends TestBase {
    @Test
    void saveCar() {
        Car car = TestUtil.getCar();
        session.save(car);
        session.clear();

        var actualCar = session.get(Car.class, car.getId());

        assertThat(actualCar).isNotNull();
    }

    @Test
    void getCar() {
        Car car = TestUtil.getCar();
        session.save(car);
        session.flush();
        session.clear();

        var actualCar = session.get(Car.class, car.getId());

        assertThat(actualCar.getRegSign()).isEqualTo(car.getRegSign());
    }

    @Test
    void updateCar() {
        Car car = TestUtil.getCar();
        String updatedRegSign = "6077 EA-2";
        session.save(car);
        car.setRegSign(updatedRegSign);
        session.update(car);
        session.flush();
        session.clear();

        Car updatedCar = session.get(Car.class, car.getId());

        assertThat(updatedCar.getRegSign()).isEqualTo(updatedRegSign);
    }

    @Test
    void deleteCar() {
        Car car = TestUtil.getCar();

        session.save(car);
        session.flush();
        session.delete(car);
        session.flush();
        session.clear();
        Car deletedCar = session.get(Car.class, car.getId());

        assertThat(deletedCar).isNull();

    }
}
