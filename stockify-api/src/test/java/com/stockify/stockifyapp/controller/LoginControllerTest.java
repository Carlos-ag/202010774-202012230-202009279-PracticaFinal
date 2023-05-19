package com.stockify.stockifyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockify.stockifyapp.model.SubscriptionPlan;
import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.repository.UserRepository;
import com.stockify.stockifyapp.service.LoginService;
import com.stockify.stockifyapp.service.SignedMessageService;
import com.stockify.stockifyapp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;


    public void setUp() {
        // Initialize test data in the database

        SubscriptionPlan su = new SubscriptionPlan("1",1);
        AggregateReference<SubscriptionPlan, Integer> subscriptionPlanRef = AggregateReference.to(su.getId());


        User user = new User();
        user.setEmail("alice@example.com");
        user.setPassword("password");
        user.setName("Alice");
        user.setPhone("1");
        user.setSubscriptionPlan(subscriptionPlanRef);
        userRepository.save(user);
    }

    @Test
    @DisplayName("POST /login correct")
    public void loginPositive() throws Exception {
        // Prepare request body
        LoginRequest request = new LoginRequest("alice@example.com", "password");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}
