package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.dto.ProductResponse;
import com.personal.ecommerce.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    @Query(value = """
                SELECT DISTINCT p.*
                FROM PRODUCT p
                JOIN product_category pc ON p.product_id = pc.product_id
                JOIN category c ON pc.category_id = c.category_id
                WHERE c.name = :categoryName
            """, nativeQuery = true)
    List<ProductEntity> findByCategory(@Param("categoryName") String categoryName);

    @Query(value = """
            SELECT * FROM product
            """, nativeQuery = true)
    Page<ProductEntity> findByPageable(Pageable pageable);
}
