package com.ecom.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecommerce_app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> 
{

    User findByUsername(String username);
}
