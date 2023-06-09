package com.stockify.stockifyapp.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.model.UnsignedMessage;
import com.stockify.stockifyapp.service.UnsignedMessageService;

@RestController
public class UnsignedMessageController {

    private static final Logger logger = LoggerFactory.getLogger(UnsignedMessageController.class);

    private UnsignedMessageService unsignedMessageService;

    public UnsignedMessageController(UnsignedMessageService unsignedMessageService) {
        this.unsignedMessageService = unsignedMessageService;
    }

    
    @PostMapping(path = "/contact", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> addContactMessage(@RequestBody Map<String, Object> message) {
        try {
            UnsignedMessage contactMessage = unsignedMessageService.addMessage(message);
            logger.info("Added contact message: " + contactMessage.toString());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Error adding contact message: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error adding contact message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
