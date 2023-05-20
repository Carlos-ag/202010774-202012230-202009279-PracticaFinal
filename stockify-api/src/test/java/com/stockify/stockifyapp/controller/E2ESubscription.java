package com.stockify.stockifyapp.controller;


import com.stockify.stockifyapp.model.SubscriptionPlan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
public class E2ESubscription {

    TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplateBuilder().rootUri("http://localhost:8081"));


    @Test
    public void positive_getSuscriptionPlanInfo()
    {
        String endpoint = "/suscriptionPlans/1";

        String name = "Basic";
        double price = 5;

        ResponseEntity<SubscriptionPlan> response = restTemplate.getForEntity(endpoint,SubscriptionPlan.class);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(name,response.getBody().getName());
        Assertions.assertEquals(price,response.getBody().getPrice());
        
    }

 

@Test
public void positive_getAllSuscriptionPlans()
{
    String endpoint = "/subscriptionPlans"; 

    // define expected subscription plans
    SubscriptionPlan basicPlan = new SubscriptionPlan("Basic", 5.0);
    basicPlan.setId(1);
    SubscriptionPlan plusPlan = new SubscriptionPlan("Plus", 20.0);
    plusPlan.setId(2);
    SubscriptionPlan executivePlan = new SubscriptionPlan("Executive", 99.0);
    executivePlan.setId(3);

    List<SubscriptionPlan> expectedSubscriptionPlans = Arrays.asList(basicPlan, plusPlan, executivePlan);

    ResponseEntity<List<SubscriptionPlan>> response = restTemplate.exchange(
        endpoint,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<SubscriptionPlan>>() {
        });

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    
    Assertions.assertEquals(expectedSubscriptionPlans.size(), response.getBody().size());
    Assertions.assertEquals(1, response.getBody().get(0).getId());
    Assertions.assertEquals("Basic", response.getBody().get(0).getName());
    Assertions.assertEquals(5.0, response.getBody().get(0).getPrice());
    Assertions.assertEquals(2, response.getBody().get(1).getId());
    Assertions.assertEquals("Plus", response.getBody().get(1).getName());
    Assertions.assertEquals(20.0, response.getBody().get(1).getPrice());
    Assertions.assertEquals(3, response.getBody().get(2).getId());
    Assertions.assertEquals("Executive", response.getBody().get(2).getName());
    Assertions.assertEquals(99.0, response.getBody().get(2).getPrice());

}


    



}
