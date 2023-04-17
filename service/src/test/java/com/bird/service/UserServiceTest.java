package com.bird.service;

import com.bird.annotation.IT;
import com.bird.dto.UserCreateEditDto;
import com.bird.dto.UserReadDto;
import com.bird.entity.Role;
import com.bird.integration.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
class UserServiceTest extends TestBase {

    private final UserService userService;
    private final Integer USER_ID_ONE = 1;
    private final Integer USER_ID_NONE = -1;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_ID_ONE);

        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getEmail()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "John",
                "Doe",
                "john@gmail.com",
                "322223",
                Role.USER,
                null
        );

        UserReadDto actualUser = userService.create(userDto);

        assertEquals(userDto.getFirstName(), actualUser.getFirstName());
        assertEquals(userDto.getLastName(), actualUser.getLastName());
        assertEquals(userDto.getEmail(), actualUser.getEmail());
        assertEquals(userDto.getPassword(), actualUser.getPassword());
        assertEquals(userDto.getRole(), actualUser.getRole());
        assertThat(userDto.getClientId()).isNull();
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "John",
                "Doe",
                "john@gmail.com",
                "322223",
                Role.USER,
                null
        );

        Optional<UserReadDto> maybeUser = userService.update(USER_ID_ONE, userDto);

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(
                user -> {
                    assertEquals(userDto.getFirstName(), user.getFirstName());
                    assertEquals(userDto.getLastName(), user.getLastName());
                    assertEquals(userDto.getEmail(), user.getEmail());
                    assertEquals(userDto.getPassword(), user.getPassword());
                    assertEquals(userDto.getRole(), user.getRole());
                    assertThat(userDto.getClientId()).isNull();
                }
        );
    }

    @Test
    void delete() {
        assertTrue(userService.delete(USER_ID_ONE));
    }

    @Test
    void deleteMissingUser() {
        assertFalse(userService.delete(USER_ID_NONE));
    }
}