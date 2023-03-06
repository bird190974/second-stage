package com.bird.integration.entity;

import com.bird.entity.User;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import com.bird.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserIT extends TestBase {

    @Test
    void saveUser() {
        User user = TestUtil.getUser();
        session.save(user);
        session.flush();
        session.clear();
        User actualUser = session.get(User.class, user.getId());

        assertThat(actualUser).isNotNull();

    }

    @Test
    void getUser() {
        User user = TestUtil.getUser();
        session.save(user);
        session.flush();
        session.clear();
        User actualUser = session.get(User.class, user.getId());

        assertThat(actualUser).isEqualTo(user);

    }

    @Test
    void updateUser() {
        User user = TestUtil.getUser();
        String updatedEmail = "newmai@gmail.com";
        session.save(user);
        session.flush();
        user.setEmail(updatedEmail);

        session.update(user);
        session.flush();
        session.clear();
        User updatedUser = session.get(User.class, user.getId());

        assertThat(updatedUser.getEmail()).isEqualTo(updatedEmail);
    }


    @Test
    void deleteUser() {
        User user = TestUtil.getUser();
        session.save(user);
        session.flush();

        session.delete(user);
        session.flush();
        session.clear();
        User deletedUser = session.get(User.class, user.getId());

        assertThat(deletedUser).isNull();
    }
}


