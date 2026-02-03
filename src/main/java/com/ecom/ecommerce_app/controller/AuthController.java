package com.ecom.ecommerce_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce_app.entity.User;
import com.ecom.ecommerce_app.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController 
{

    @Autowired
    private AuthService authService;

    // User Registration
    @PostMapping("/register")
    public String register(@RequestBody User user) 
    {
        return authService.register(user);
    }

    // User Login (JWT Token)
    @PostMapping("/login")
    public String login(@RequestBody User user) 
    {
        return authService.login(user);
    }
}
