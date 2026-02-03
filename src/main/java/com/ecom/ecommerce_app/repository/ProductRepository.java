package com.ecom.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.ecommerce_app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> 
{
	
}
