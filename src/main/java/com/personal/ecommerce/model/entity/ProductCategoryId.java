package com.personal.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Embeddable
public class ProductCategoryId implements Serializable {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_id")
    private Long categoryId;
}
