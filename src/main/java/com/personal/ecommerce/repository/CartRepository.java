package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    boolean existsByUserId(Long userId);

    Optional<CartEntity> findByUserId(Long userId);

    @Query("SELECT c FROM CartEntity c LEFT JOIN FETCH c.cartItems ci WHERE c.cartId = :cartId AND ci.productId IN (:productIds)")
    CartEntity findByCartIdWithItems(Long cartId, List<Long> productIds);
}
