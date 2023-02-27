package com.bird.integration.entity;

import com.bird.entity.User;
import com.bird.testUtils.TestUtil;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void saveAndGetUser() {
        User user = TestUtil.getUser();

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.flush();
            session.evict(user);
            var result = session
                    .createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", "data@gmail.com")
                    .list();

            assertThat(result.get(0).getEmail()).isEqualTo(user.getEmail());

            session.getTransaction().commit();
        }
    }
    @Test
    void updateUser() {
        User user = TestUtil.getUser();
        String updatedEmail = "newmai@gmail.com";

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            user.setEmail(updatedEmail);
            session.update(user);
            session.flush();
            session.evict(user);

            User updatedUser = session.get(User.class, user.getId());

            assertThat(updatedUser.getEmail()).isEqualTo(updatedEmail);

            session.getTransaction().commit();
        }
    }


    @Test
    void deleteUser() {
        User user = TestUtil.getUser();
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(user);
            session.delete(user);

            User deletedUser = session.get(User.class, user.getId());

            assertThat(deletedUser).isNull();

            session.getTransaction().commit();
        }
    }
}


