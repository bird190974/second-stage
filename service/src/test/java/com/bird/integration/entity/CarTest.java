package com.bird.integration.entity;

import com.bird.entity.Car;
import com.bird.testUtils.TestUtil;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {
    @Test
    void saveAndGetCar() {
        Car car = TestUtil.getCar();

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(car);
            session.flush();
            session.evict(car);
            var result = session
                    .createQuery("select c from Car c where c.regSign = :reg_sign", Car.class)
                    .setParameter("reg_sign", "5054 IT-2")
                    .list();

            assertThat(result.get(0).getRegSign()).isEqualTo(car.getRegSign());

            session.getTransaction().commit();
        }
    }

    @Test
    void updateCar() {
        Car car = TestUtil.getCar();
        String updatedRegSign = "6077 EA-2";

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(car);
            car.setRegSign(updatedRegSign);
            session.update(car);
            session.flush();
            session.evict(car);

            Car updatedCar = session.get(Car.class, car.getId());

            assertThat(updatedCar.getRegSign()).isEqualTo(updatedRegSign);
            session.getTransaction().commit();
        }
    }

    @Test
    void deleteCar() {
        Car car = TestUtil.getCar();
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(car);
            session.delete(car);

            Car deletedCar = session.get(Car.class, car.getId());

            assertThat(deletedCar).isNull();

            session.getTransaction().commit();
        }
    }
}
