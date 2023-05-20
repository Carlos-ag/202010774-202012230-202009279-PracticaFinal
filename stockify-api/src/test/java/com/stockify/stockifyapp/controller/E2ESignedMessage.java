package com.stockify.stockifyapp.controller;


import com.stockify.stockifyapp.model.SignedMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class E2ESignedMessage {
    TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:8081"));



    @Test
    public void positive_getLastId()
    {
        String endpoint = "/lastConversationId";

        Integer conversationId = 1;

        ResponseEntity<Integer> response = restTemplate.getForEntity(endpoint,Integer.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());


        assertEquals(conversationId,response.getBody());
    }

}
