package com.thisaster.testtask.user.controller;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.repository.UserRepository;
import com.thisaster.testtask.user.service.UserService;
import jakarta.transaction.Transactional;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {
        "/sql/test.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SubscriptionMapper subscriptionMapper;

    @Value("${auth-service.token}")
    String token;

    @Test
    @Transactional
    void shouldGetUserById() throws Exception {
        User user = userRepository.findById(1L).get();
        UserDTO userDTO = userMapper.toDTO(user);
        Subscription subscription = subscriptionRepository.findById(1L).get();
        SubscriptionDTO subscriptionDTO = subscriptionMapper.toDTO(subscription);
        userService.subscribeUserToSub(1L, subscription);
        Set<SubscriptionDTO> subscriptionDTOs = new HashSet<>();
        subscriptionDTOs.add(subscriptionDTO);
        userDTO.setSubscriptions(subscriptionDTOs);

        MvcResult mvcResult = mockMvc.perform(get("/api/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.subscriptions[0].id", is(1)))
                .andExpect(jsonPath("$.subscriptions[0].name", is("Premium Plan")))
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
        Long userId = 3L;
        log.info(userRepository.findById(userId).get().getUsername());
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User with id " + userId + " successfully deleted.")));
    }

}
