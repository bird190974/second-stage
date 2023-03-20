package com.bird.dao;

import com.bird.dto.UserFilter;
import com.bird.entity.Role;
import com.bird.integration.TestBase;
import com.bird.testUtils.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRepositoryIT extends TestBase {

    private final UserRepository userRepository = context.getBean(UserRepository.class);

    @Test
    void save() {
        var user = TestUtil.getUser();

        var actualUser = userRepository.save(user);

        assertThat(actualUser.getId()).isNotNull();
    }

    @Test
    void update() {
        var expectedUser = userRepository.findById(1).get();
        expectedUser.setRole(Role.ADMIN);
        userRepository.update(expectedUser);
        session.clear();

        var actualUser = userRepository.findById(1);

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void delete() {
        var user = TestUtil.getUser();

        userRepository.save(user);
        userRepository.delete(user);
        session.clear();

        var maybeUser = userRepository.findById(user.getId());

        assertThat(maybeUser).isEmpty();
    }
    @Test
    void findById() {
        var actualUser = userRepository.findById(1);
        session.clear();

        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void findAll() {
        var actualUser = userRepository.findAll();
        session.clear();

        assertNotNull(actualUser);
        assertThat(actualUser).hasSize(3);
    }

    @Test
    void findByFilterWithAllParams() {
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
        var userFilter = UserFilter.builder()
                .build();

        var users = userRepository.findByFilter(userFilter);

        assertThat(users).hasSize(userRepository.findAll().size());
    }
}