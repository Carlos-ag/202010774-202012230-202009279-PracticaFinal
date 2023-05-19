package com.stockify.stockifyapp.controller;

import com.stockify.stockifyapp.model.User;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
class LoginTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test()
    public void login() throws URISyntaxException
    {
        final String baseUrl = "http://localhost:8081/login";
        URI uri = new URI(baseUrl);

        LoginRequest lr = new LoginRequest();
        lr.setPassword("password");
        lr.setEmail("alice@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<LoginRequest> request = new HttpEntity<>(lr,headers);


        ResponseEntity<HashMap<String,Object>> result = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<HashMap<String,Object>>() {}
        );




        assertEquals(HttpStatus.OK, result.getStatusCode());

        HashMap<String, Object> responseBody = result.getBody();
        String name = (String) responseBody.get("name");

        assertEquals("Alice", name);

    }


}
