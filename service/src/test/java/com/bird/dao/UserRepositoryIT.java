package com.bird.dao;

import com.bird.dto.UserFilter;
import com.bird.entity.Role;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRepositoryIT extends TestBase {


    @Test
    void save() {

        var user = TestUtil.getUser();
        var userRepository = new UserRepository(session);

        var actualUser = userRepository.save(user);

        assertThat(actualUser).isNotNull();
    }

    @Test
    void update() {
        var userRepository = new UserRepository(session);

        var expectedUser = userRepository.findById(1).get();
        expectedUser.setRole(Role.ADMIN);
        userRepository.update(expectedUser);

        var actualUser = userRepository.findById(1);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.get().getRole()).isEqualTo(Role.ADMIN);
    }


    @Test
    void deleteNotExistingUser() {
        var userRepository = new UserRepository(session);

        assertThrows(IllegalArgumentException.class, () -> userRepository.delete(60000));
    }
    @Test
    void findById() {
        var userRepository = new UserRepository(session);

        var actualUser = userRepository.findById(1);

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void findAll() {
        var userRepository = new UserRepository(session);

        var actualUser = userRepository.findAll();

        assertNotNull(actualUser);
        assertThat(actualUser).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
        var userRepository = new UserRepository(session);

        var userFilter = UserFilter.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivan@gmail.com")
                .password("1111")
                .role(Role.USER)
                .build();
        var users = userRepository.findByFilter(userFilter);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void findByFilterWithTwoParams() {
        var userRepository = new UserRepository(session);

        var userFilter = UserFilter.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();
        var users = userRepository.findByFilter(userFilter);


        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void findByFilterWithNoParams() {
        var userRepository = new UserRepository(session);

        var userFilter = UserFilter.builder()
                .build();
        var users = userRepository.findByFilter(userFilter);

        assertThat(users).hasSize(userRepository.findAll().size());

    }

}