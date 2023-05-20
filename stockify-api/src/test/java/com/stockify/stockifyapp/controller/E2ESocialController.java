package com.stockify.stockifyapp.controller;


import com.stockify.stockifyapp.model.Social;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class E2ESocialController {

    TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:8081"));

    @Test
    public void getAllPositive()
    {
        String endpoint = "/socials";


        ResponseEntity<Iterable<Social>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Social>>() {}
        );


        assertEquals(HttpStatus.OK,response.getStatusCode());

        Iterable<Social> socialList = response.getBody();
        List<String> expectedNames = List.of("Alice", "Bob", "Carol");



        List<String> actualNames = new ArrayList<>();
        for (Social social : socialList) {
            actualNames.add(social.getName());
        }

        assertEquals(expectedNames, actualNames);

    }

    @Test
    public void getUsersByNameTest() {
        // Set up the request URL
        String endpoint = "http://localhost:" + 8081 + "/socials/search?name=Alice";

        // Make the HTTP GET request
        ResponseEntity<List<Social>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Social>>() {}
        );

        // Assert the HTTP status code is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Get the list of Social objects from the response body
        List<Social> socialList = response.getBody();

        // Assert that the socialList is not null
        assert socialList != null;

        // Define the expected names
        List<String> expectedNames = List.of("Alice");

        // Extract the names from the actual list
        List<String> actualNames = new ArrayList<>();
        for (Social social : socialList) {
            actualNames.add(social.getName());
        }

        // Compare the expected names with the actual names
        assertEquals(expectedNames, actualNames);
    }


}



