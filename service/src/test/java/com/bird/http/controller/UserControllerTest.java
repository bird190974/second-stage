package com.bird.http.controller;

import com.bird.integration.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.bird.dto.UserCreateEditDto.Fields.clientId;
import static com.bird.dto.UserCreateEditDto.Fields.email;
import static com.bird.dto.UserCreateEditDto.Fields.firstName;
import static com.bird.dto.UserCreateEditDto.Fields.lastName;
import static com.bird.dto.UserCreateEditDto.Fields.password;
import static com.bird.dto.UserCreateEditDto.Fields.role;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends TestBase {

    private static final Integer USER_ID = 1;
    private static final Integer USER_NOT_EXIST_ID = 9999;
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/users"),
                        model().attributeExists("users"),
                        model().attribute("users", hasSize(3))
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/" + USER_ID.toString()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/user"),
                        model().attributeExists("user")
                );
    }

    @Test
    void findByMissingId() throws Exception {
        mockMvc.perform(get("/users/" + USER_NOT_EXIST_ID.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(firstName, "Test")
                        .param(lastName, "Test")
                        .param(email, "test@gmail.com")
                        .param(password, "322223")
                        .param(role, "ADMIN")
                        .param(clientId, USER_NOT_EXIST_ID.toString())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/" + USER_ID.toString() + "/update")
                        .param(firstName, "Ivan")
                        .param(lastName, "Ivanov")
                        .param(email, "ivan@gmail.com")
                        .param(password, "1111")
                        .param(role, "ADMIN")
                        .param(clientId, "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + USER_ID.toString())
                );
        mockMvc.perform(post("/users/" + USER_NOT_EXIST_ID.toString() + "/update"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/" + USER_ID.toString() + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
        mockMvc.perform(post("/users/" + USER_NOT_EXIST_ID.toString() + "/delete"))
                .andExpect(status().is4xxClientError());
    }
}