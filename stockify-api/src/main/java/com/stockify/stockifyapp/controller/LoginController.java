package com.stockify.stockifyapp.controller;

import com.stockify.stockifyapp.common.PasswordEncrypter;
import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.service.LoginService;
import com.stockify.stockifyapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping(path="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> searchUser(@RequestBody LoginRequest message, BindingResult bindingResult) throws Exception {
        PasswordEncrypter pwe = new PasswordEncrypter();
        System.out.println(pwe.encrypt(message.getPassword()));
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(loginService.findUser(pwe.encrypt(message.getPassword()), message.getEmail()));
    }


}