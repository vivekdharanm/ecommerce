package com.ecom.ecommerce_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce_app.entity.Cart;
import com.ecom.ecommerce_app.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController 
{

    @Autowired
    private CartService cartService;

    // Add product to cart
    @PostMapping("/add")
    public Cart addToCart(@RequestBody Cart cart) 
    {
        return cartService.addToCart(cart);
    }

    // View cart items for a user
    @GetMapping("/user/{userId}")
    public List<Cart> viewCart(@PathVariable Long userId) 
    {
        return cartService.getCartByUser(userId);
    }

    // Remove cart item
    @DeleteMapping("/{cartId}")
    public String removeItem(@PathVariable Long cartId) 
    {
        cartService.removeFromCart(cartId);
        return "Item removed from cart";
    }
}
