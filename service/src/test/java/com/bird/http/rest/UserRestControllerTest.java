package com.bird.http.rest;

import com.bird.dto.UserCreateEditDto;
import com.bird.entity.Role;
import com.bird.integration.TestBase;
import com.bird.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserRestControllerTest extends TestBase {

    private static final Integer USER_ID = 1;
    private static final Integer USER_NOT_EXIST_ID = -1;
    private final MockMvc mockMvc;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpectAll(
                        status().is2xxSuccessful()
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + USER_ID.toString()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.ALL.APPLICATION_JSON),
                        jsonPath("$.email").value("ivan@gmail.com"));
    }

    @Test
    void findByMissingId() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + USER_NOT_EXIST_ID.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create() throws Exception {
        var user = new UserCreateEditDto(
                "test",
                "test",
                "test@gmail.com",
                "322223",
                Role.USER,
                null
        );
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.email").value(user.getEmail())
                );
    }

    @Test
    void update() throws Exception {
        var user = userService.findById(USER_ID);
        var updatedUser = new UserCreateEditDto(
                "John",
                "Doe",
                user.get().getEmail(),
                user.get().getPassword(),
                user.get().getRole(),
                user.get().getClient().id()
        );

        mockMvc.perform(put("/api/v1/users/" + USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.firstName").value(updatedUser.getFirstName()),
                        jsonPath("$.lastName").value(updatedUser.getLastName())
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + USER_ID.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMissingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/" + USER_NOT_EXIST_ID.toString()))
                .andExpect(status().is4xxClientError());
    }
}