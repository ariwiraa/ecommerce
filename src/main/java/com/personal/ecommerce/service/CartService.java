package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.CartItemResponse;

import java.util.List;

public interface CartService {
    void addItemToCart(Long userId, Long productId, int quantity);

    void updateItemQuantity(Long userId, Long productId, int quantity);

    void removeItemFromCart(Long userId, Long productId);

    void clearCart(Long userId);

    List<CartItemResponse> getCartItems(Long userId);

}
