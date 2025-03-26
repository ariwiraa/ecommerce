package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.PaginatedProductResponse;
import com.personal.ecommerce.model.dto.ProductRequest;
import com.personal.ecommerce.model.dto.ProductResponse;
import com.personal.ecommerce.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    String createProduct(ProductRequest request);

    List<ProductResponse> getProductByName(String name);

    PaginatedProductResponse getByPage(Pageable pageable);
}
