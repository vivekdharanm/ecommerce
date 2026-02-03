package com.ecom.ecommerce_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce_app.entity.Product;
import com.ecom.ecommerce_app.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController 
{

    @Autowired
    private ProductService productService;

    // Public - View all products
    @GetMapping
    public List<Product> getAll() 
    {
        return productService.getAllProducts();
    }

    // ADMIN only - Add product
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public Product add(@RequestBody Product product) 
    {
        return productService.addProduct(product);
    }
}
