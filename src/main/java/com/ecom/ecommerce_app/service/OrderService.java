package com.ecom.ecommerce_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.ecommerce_app.entity.Cart;
import com.ecom.ecommerce_app.entity.Order;
import com.ecom.ecommerce_app.entity.Product;
import com.ecom.ecommerce_app.repository.CartRepository;
import com.ecom.ecommerce_app.repository.OrderRepository;
import com.ecom.ecommerce_app.repository.ProductRepository;

@Service
public class OrderService 
{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order placeOrder(Long userId) 
    {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cart is empty"
            );
        }

        double total = 0;

        for (Cart item : cartItems) 
        {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Product not found"
                    ));

            if (product.getStock() < item.getQuantity())
            {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient stock for product: " + product.getName()
                );
            }

            total += product.getPrice() * item.getQuantity();

            // reduce stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        // clear cart AFTER order success
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) 
    {

        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No orders found"
            );
        }

        return orders;
    }
}
