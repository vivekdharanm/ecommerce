package com.ecom.ecommerce_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.ecommerce_app.entity.Product;
import com.ecom.ecommerce_app.repository.ProductRepository;

@Service
public class ProductService 
{

    @Autowired
    private ProductRepository productRepository;

    // ADD PRODUCT
    public Product addProduct(Product product) 
    {

        if (product.getName() == null || product.getName().isEmpty()) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product name is required"
            );
        }

        if (product.getPrice() <= 0) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Price must be greater than 0"
            );
        }

        if (product.getStock() < 0) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Stock cannot be negative"
            );
        }

        return productRepository.save(product);
    }

    // GET ALL PRODUCTS
    public List<Product> getAllProducts() 
    {

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) 
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No products available"
            );
        }

        return products;
    }
}
