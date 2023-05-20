package com.stockify.stockifyapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.stockify.stockifyapp.model.PortfolioMovement;
import com.stockify.stockifyapp.service.PortfolioService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class E2EPortfolio {

    TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:8081"));

    @Test
@DisplayName("GET /portfolio/userID debe retornar la lista todos los movimientos de un usuario")
public void getPortfolio() {
    String endpoint = "/portfolio/1";
    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            endpoint,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {
            });

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    
    Map<String, Object> firstEntry = response.getBody().get(0);
    Assertions.assertEquals(1, firstEntry.get("id"));
    Assertions.assertEquals("AAPL", firstEntry.get("ticker"));
    Assertions.assertEquals(10, firstEntry.get("quantity"));
    Assertions.assertEquals(150.0, firstEntry.get("price"));
    Assertions.assertEquals("2023-04-19", firstEntry.get("date"));
    Assertions.assertEquals(1, firstEntry.get("userId"));

    Map<String, Object> secondEntry = response.getBody().get(1);
    Assertions.assertEquals(2, secondEntry.get("id"));
    Assertions.assertEquals("GOOG", secondEntry.get("ticker"));
    Assertions.assertEquals(5, secondEntry.get("quantity"));
    Assertions.assertEquals(1200.0, secondEntry.get("price"));
    Assertions.assertEquals("2023-04-19", secondEntry.get("date"));
    Assertions.assertEquals(1, secondEntry.get("userId"));
}


@Test
@DisplayName("POST /portfolio/movement debe agregar un movimiento al portfolio")
public void addMovement() {
    String endpoint = "/movement";
    PortfolioMovement payload = new PortfolioMovement("AAPL", 10, 150.0, LocalDate.parse("2023-04-19"),1);

    ResponseEntity<PortfolioMovement> response = restTemplate.postForEntity(endpoint, payload, PortfolioMovement.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    PortfolioMovement responseBody = response.getBody();
    Assertions.assertEquals(5, responseBody.getId());
    Assertions.assertEquals("AAPL", responseBody.getTicker());
    Assertions.assertEquals(10, responseBody.getQuantity());
    Assertions.assertEquals(150.0, responseBody.getPrice());
    Assertions.assertEquals(LocalDate.parse("2023-04-19"), responseBody.getDate());
    Assertions.assertEquals(1, responseBody.getUserId());
}


@Test
@DisplayName("POST /portfolio/movement/update debe actualizar un movimiento del portfolio")
public void updateMovement() {
    String endpoint = "/movement/update";
    PortfolioMovement payload = new PortfolioMovement(1, "AAPL", 10, 150.0, LocalDate.parse("2023-04-19"),1);

    ResponseEntity<PortfolioMovement> response = restTemplate.postForEntity(endpoint, payload, PortfolioMovement.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    PortfolioMovement responseBody = response.getBody();
    Assertions.assertEquals(1, responseBody.getId());
    Assertions.assertEquals("AAPL", responseBody.getTicker());
    Assertions.assertEquals(10, responseBody.getQuantity());
    Assertions.assertEquals(150.0, responseBody.getPrice());
    Assertions.assertEquals(LocalDate.parse("2023-04-19"), responseBody.getDate());
    Assertions.assertEquals(1, responseBody.getUserId());
}
    
@Test
@DisplayName("GET /portfolio/movement/{movementID} debe retornar un movimiento del portfolio")
public void getMovement() {
    String endpoint = "/movement/1";
    ResponseEntity<PortfolioMovement> response = restTemplate.getForEntity(endpoint, PortfolioMovement.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    PortfolioMovement responseBody = response.getBody();
    Assertions.assertEquals(1, responseBody.getId());
    Assertions.assertEquals("AAPL", responseBody.getTicker());
    Assertions.assertEquals(10, responseBody.getQuantity());
    Assertions.assertEquals(150.0, responseBody.getPrice());
    Assertions.assertEquals(LocalDate.parse("2023-04-19"), responseBody.getDate());
    Assertions.assertEquals(1, responseBody.getUserId());
}

@Test
@DisplayName("DELETE /portfolio/movement/{movementID} debe eliminar un movimiento del portfolio")
public void deleteMovement() {
    String endpoint = "/movement/1";
    restTemplate.delete(endpoint);

    ResponseEntity<PortfolioMovement> response = restTemplate.getForEntity(endpoint, PortfolioMovement.class);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
}

}
