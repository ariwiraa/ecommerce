package com.personal.ecommerce.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.personal.ecommerce.model.entity.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse {
    private Long productId;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer stockQuantity;
    private BigDecimal weight;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<CategoryResponse> categories;

    public static ProductResponse fromProductResponseAndCategories(ProductEntity product,
                                                                   List<CategoryResponse> categories) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(product.getProductId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setWeight(product.getWeight());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        productResponse.setCategories(categories);

        return productResponse;
    }
}
