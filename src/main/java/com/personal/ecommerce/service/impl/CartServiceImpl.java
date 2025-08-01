package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.common.errors.BadRequestException;
import com.personal.ecommerce.common.errors.ForbiddenAccessException;
import com.personal.ecommerce.common.errors.ResourceNotFoundException;
import com.personal.ecommerce.model.dto.CartItemResponse;
import com.personal.ecommerce.model.entity.CartEntity;
import com.personal.ecommerce.model.entity.CartItemEntity;
import com.personal.ecommerce.model.entity.ProductEntity;
import com.personal.ecommerce.repository.CartItemRepository;
import com.personal.ecommerce.repository.CartRepository;
import com.personal.ecommerce.repository.ProductRepository;
import com.personal.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional
    public void addItemToCart(Long userId, Long productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(() -> {
            CartEntity cart = new CartEntity();
            cart.setUserId(userId);
            return cartRepository.save(cart);
        });

        ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product not found."));

        if (product.getUserId().equals(userId)) {
            throw new BadRequestException("User cannot add their own product to cart.");
        }

        CartItemEntity cartItemEntity = cartItemRepository.findByCartIdAndProductId(cartEntity.getCartId(), productId);
        if (cartItemEntity != null) {
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
            cartItemRepository.save(cartItemEntity);
        } else {
            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setCart(cartEntity);
            cartItem.setProductId(product.getProductId());
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long userId, Long productId, int quantity) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found."));

        CartItemEntity cartItemEntity = cartItemRepository.findByCartIdAndProductId(cartEntity.getCartId(), productId);

        if (cartItemEntity == null) {
            throw new ResourceNotFoundException("Cart item not found.");
        }

        if (!cartItemEntity.getCart().getCartId().equals(cartEntity.getCartId())) {
            throw new ForbiddenAccessException("User cannot remove other user's cart item.");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(cartItemEntity);
        } else {
            cartItemEntity.setQuantity(quantity);
            cartItemRepository.save(cartItemEntity);
        }

    }

    @Override
    @Transactional
    public void removeItemFromCart(Long userId, Long cartItemId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found."));

        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(cartItemId);

        if (cartItemEntity.isEmpty()) {
            throw new ResourceNotFoundException("Cart item not found.");
        }

        CartItemEntity cartItem = cartItemEntity.get();
        if (!cartItem.getCart().getCartId().equals(cartEntity.getCartId())) {
            throw new ForbiddenAccessException("User cannot remove other user's cart item.");
        }

        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found."));
        cartItemRepository.deleteAllByCartId(cartEntity.getCartId());
    }

    @Override
    public List<CartItemResponse> getCartItems(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findUserCartItems(userId);

        if (cartItems.isEmpty()) {
            return List.of();
        }

        List<Long> productIds = cartItems.stream().map(CartItemEntity::getProductId).toList();

        List<ProductEntity> products = productRepository.findAllById(productIds);

        Map<Long, ProductEntity> productMap = products.stream().collect(
                Collectors.toMap(ProductEntity::getProductId, product -> product));

        return cartItems.stream().map(cartItem -> {
            ProductEntity product = productMap.get(cartItem.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Product not found.");
            }
            return CartItemResponse.fromCartItemResponse(cartItem, product);
        }).toList();
    }
}
