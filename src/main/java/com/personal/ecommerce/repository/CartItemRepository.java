package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    @Query(value = """
            SELECT ci.*
            FROM cart_items ci
            JOIN carts c ON ci.cart_id = c.cart_id
            WHERE user_id = :userId
            """, nativeQuery = true)
    List<CartItemEntity> findUserCartItems(@Param("userId") Long userId);

    @Query(value = """
            SELECT ci.*
            FROM cart_items ci
            WHERE ci.cart_id = :cartId AND ci.product_id = :productId
            """, nativeQuery = true)
    CartItemEntity findByCartIdAndProductId(@Param("cartId")Long cartId, @Param("productId")Long productId);

    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE cart_id = :cartId", nativeQuery = true)
    void deleteAllByCartId(Long cartId);

    @Query(value = """
            SELECT ci.*
            FROM cart_items ci
            LEFT JOIN carts c ON ci.cart_id = c.cart_id
            WHERE ci.cart_id = :cartId AND ci.product_id IN (:productIds)
            """, nativeQuery = true)
    List<CartItemEntity> findByCartIdAndProductIds(@Param("cartId")Long cartId, @Param("productIds")List<Long> productIds);
}
