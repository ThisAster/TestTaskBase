package com.thisaster.testtask.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${auth-service.token}")
    String token;

    @Test
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
    void shouldDeleteUserById() throws Exception {
        Long userId = 2L;
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User with id " + userId + " successfully deleted.")));
    }

}
