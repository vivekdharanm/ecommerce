package com.ecom.ecommerce_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.ecommerce_app.entity.Cart;
import com.ecom.ecommerce_app.repository.CartRepository;

@Service
public class CartService 
{

    @Autowired
    private CartRepository cartRepository;

    // ADD TO CART
    public Cart addToCart(Cart cart) 
    {

        if (cart.getQuantity() <= 0) 
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than 0"
            );
        }

        return cartRepository.save(cart);
    }

    // GET CART BY USER
    public List<Cart> getCartByUser(Long userId) 
    {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) 
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cart is empty"
            );
        }

        return cartItems;
    }

    // REMOVE FROM CART
    public void removeFromCart(Long cartId, Long userId) 
    {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cart item not found"
                ));

        // ownership check
        if (!cart.getUserId().equals(userId)) 
        {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to delete this cart item"
            );
        }

        cartRepository.delete(cart);
    }
}
