package com.ecom.ecommerce_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecommerce_app.entity.Order;
import com.ecom.ecommerce_app.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController 
{

    @Autowired
    private OrderService orderService;

    // Place order
    @PostMapping("/place/{userId}")
    public Order placeOrder(@PathVariable Long userId) 
    {
        return orderService.placeOrder(userId);
    }

    // View orders by user
    @GetMapping("/user/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) 
    {
        return orderService.getOrdersByUser(userId);
    }
}
