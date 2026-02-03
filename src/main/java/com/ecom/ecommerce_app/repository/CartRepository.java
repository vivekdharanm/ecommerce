package com.ecom.ecommerce_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecommerce_app.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> 
{

    List<Cart> findByUserId(Long userId);
}
