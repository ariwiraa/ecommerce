package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.ProductCategoryEntity;
import com.personal.ecommerce.model.entity.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, ProductCategoryId> {
}
