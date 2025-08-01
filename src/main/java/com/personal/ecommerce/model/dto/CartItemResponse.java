package com.personal.ecommerce.model.dto;

import com.personal.ecommerce.model.entity.CartItemEntity;
import com.personal.ecommerce.model.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class CartItemResponse implements Serializable {
    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private String productName;
    private BigDecimal weight;
    private BigDecimal totalPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static CartItemResponse fromCartItemResponse(CartItemEntity cartItem, ProductEntity product) {
        BigDecimal totalPrice = cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        BigDecimal weight = product.getWeight().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        return CartItemResponse.builder()
                .cartItemId(cartItem.getCartItemId())
                .productId(product.getProductId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .productName(product.getName())
                .weight(weight)
                .totalPrice(totalPrice)
                .createdAt(cartItem.getCreatedAt())
                .updatedAt(cartItem.getUpdatedAt())
                .build();
    }
}
