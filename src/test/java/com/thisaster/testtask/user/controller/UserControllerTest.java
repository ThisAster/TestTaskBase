package com.thisaster.testtask.user.controller;

import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import com.thisaster.testtask.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${auth-service.token}")
    String token;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    void shouldGetUserById() throws Exception {
        Long userId = 1L;

        MvcResult mvcResult = mockMvc.perform(get("/api/users/{id}", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("admin")))
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    void shouldAddSubscriptionToUser(@Value("classpath:user/createSubscription.json") Resource json) throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("/api/users/{id}/subscriptions", userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("subscribe to")));
    }

    @Test
    @Order(3)
    void shouldDeleteUserById() throws Exception {
        Long userId = 2L;
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User with id " + userId + " successfully deleted.")));
    }

    @Test
    @Order(4)
    void shouldGetUserSubscriptions() throws Exception {
        final Long userId = 3L;

        mockMvc.perform(get("/api/users/{id}/subscriptions", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Premium Plan")))
                .andExpect(jsonPath("$[1].name", is("Newsletter")))
                .andExpect(jsonPath("$[2].name", is("Basic Access")));
    }

    @Test
    @Order(5)
    void shouldDeleteUserSubscription() throws Exception {
        final Long userId = 3L;
        final Long subscriptionId = 3L;
        mockMvc.perform(delete("/api/users/{userId}/subscriptions/{subscriptionId}", userId, subscriptionId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully deleted subscription")));
    }


    @Test
    @Order(6)
    void shouldUpdateUser(@Value("classpath:user/updateUser.json") Resource json) throws Exception {
        Long userId = 3L;
        mockMvc.perform(put("/api/users/{userId}", userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getContentAsByteArray()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.username", is("maksim_updated")))
                .andExpect(jsonPath("$.password", is(userRepository.findById(userId).get().getPassword())))
                .andExpect(jsonPath("$.email", is("maksim_updated@test.ru")))
                .andExpect(jsonPath("$.subscriptions", hasSize(3)))
                .andExpect(jsonPath("$.subscriptions[*].name", containsInAnyOrder(
                        "Premium Plan", "Newsletter", "Yandex Premium"
                )));

        contains(subscriptionRepository.existsByName("Yandex Premium"));
    }


}
