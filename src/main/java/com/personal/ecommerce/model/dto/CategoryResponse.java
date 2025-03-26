package com.personal.ecommerce.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.personal.ecommerce.model.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryResponse {
    private Long categoryId;
    private String name;

    public static CategoryResponse fromCategory(CategoryEntity category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
