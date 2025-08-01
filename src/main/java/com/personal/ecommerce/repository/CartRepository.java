package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    boolean existsByUserId(Long userId);

    Optional<CartEntity> findByUserId(Long userId);
}
