package com.ecom.ecommerce_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.ecommerce_app.entity.User;
import com.ecom.ecommerce_app.repository.UserRepository;
import com.ecom.ecommerce_app.security.JwtUtil;

@Service
public class AuthService 
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    public String register(User user) 
    {

        // username already exists check
        if (userRepository.findByUsername(user.getUsername()) != null) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Username already exists"
            );
        }

        user.setRole("USER");

        // password encrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // LOGIN
    public String login(User user) 
    {

        User dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser == null) 
        {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Credentials"
            );
        }

        // password match
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) 
        {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Credentials"
            );
        }

        // generate JWT token
        return jwtUtil.generateToken(dbUser.getUsername());
    }
}
