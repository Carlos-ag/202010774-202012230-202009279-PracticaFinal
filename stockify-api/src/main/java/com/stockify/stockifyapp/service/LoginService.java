
package com.stockify.stockifyapp.service;

import com.stockify.stockifyapp.controller.LoginController;
import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.repository.SuscriptionPlanRepository;
import com.stockify.stockifyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuscriptionPlanRepository subscriptionPlanRepository;

    public LoginService()
    {

    }

    public User findUser(String password,String email)
    {
        return userRepository.getUser(email,password);
    }
}
