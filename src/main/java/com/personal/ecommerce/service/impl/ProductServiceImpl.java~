package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.common.errors.ResourceNotFoundException;
import com.personal.ecommerce.constants.Constants;
import com.personal.ecommerce.model.dto.CategoryResponse;
import com.personal.ecommerce.model.dto.PaginatedProductResponse;
import com.personal.ecommerce.model.dto.ProductRequest;
import com.personal.ecommerce.model.dto.ProductResponse;
import com.personal.ecommerce.model.entity.CategoryEntity;
import com.personal.ecommerce.model.entity.ProductCategoryEntity;
import com.personal.ecommerce.model.entity.ProductCategoryId;
import com.personal.ecommerce.model.entity.ProductEntity;
import com.personal.ecommerce.repository.CategoryRepository;
import com.personal.ecommerce.repository.ProductCategoryRepository;
import com.personal.ecommerce.repository.ProductRepository;
import com.personal.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public String createProduct(ProductRequest request) {
        String response = Constants.EMPTY_STRING;
        try {
            log.info("[ProductService - createProduct] started");
            List<CategoryEntity> categoryEntities = categoryRepository.findByCategoryIdIn(request.getCategories());
            if (categoryEntities.isEmpty()) {
                throw new ResourceNotFoundException("Tidak ada Category tersebut");
            }

            ProductEntity product = new ProductEntity();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);
            product.setWeight(request.getWeight() != null ? request.getWeight() : BigDecimal.valueOf(0));
            product.setCategories(categoryEntities);

            ProductEntity productEntity = productRepository.save(product);

            for (CategoryEntity category : categoryEntities) {
                ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity();
                ProductCategoryId productCategoryId = new ProductCategoryId();
                productCategoryId.setProductId(productEntity.getProductId());
                productCategoryId.setCategoryId(category.getCategoryId());

                productCategoryEntity.setId(productCategoryId);
                productCategoryRepository.save(productCategoryEntity);
            }

            log.info("[ProductService - createProduct] ended");
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "Success";
    }

    @Override
    public List<ProductResponse> getProductByName(String name) {
        List<ProductEntity> productEntities =  productRepository.findByNameContainingIgnoreCase(name);

        List<CategoryResponse> categoryResponses = convertToListCategoryResponse(productEntities);

        return convertToListProductResponse(productEntities, categoryResponses);
    }

    @Override
    public PaginatedProductResponse getByPage(Pageable pageable) {
         Page<ProductResponse> productResponses = productRepository.findByPageable(pageable)
                 .map(product -> {
                     List<CategoryResponse> categoryResponses = convertToListCategoryResponse(product);
                     return ProductResponse.fromProductResponseAndCategories(product, categoryResponses);
                 });

         return convertProductPage(productResponses);
    }

    private List<CategoryResponse> convertToListCategoryResponse(List<ProductEntity> productEntities) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();

        List<CategoryEntity> categoryEntities = new ArrayList<>();

        for (ProductEntity product : productEntities) {
            categoryEntities.addAll(product.getCategories());
        }

        for (CategoryEntity category : categoryEntities) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setName(category.getName());
            categoryResponses.add(categoryResponse);
        }

        return categoryResponses;
    }

    private List<CategoryResponse> convertToListCategoryResponse(ProductEntity productEntities) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();


        for (CategoryEntity category : productEntities.getCategories()) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setName(category.getName());
            categoryResponses.add(categoryResponse);
        }

        return categoryResponses;
    }

    private List<ProductResponse> convertToListProductResponse(List<ProductEntity> productEntities,
                                                               List<CategoryResponse> categoryResponses) {
        List<ProductResponse> productResponses = new ArrayList<>();

        for (ProductEntity product : productEntities) {
            ProductResponse productResponse = ProductResponse.fromProductResponseAndCategories(product, categoryResponses);

            productResponses.add(productResponse);
        }

        return productResponses;
    }

    private PaginatedProductResponse convertProductPage(Page<ProductResponse> productResponses) {
        PaginatedProductResponse paginatedProductResponse = new PaginatedProductResponse();

        paginatedProductResponse.setData(productResponses.getContent());
        paginatedProductResponse.setPageNo(productResponses.getNumber());
        paginatedProductResponse.setPageSize(productResponses.getSize());
        paginatedProductResponse.setTotalElements(paginatedProductResponse.getTotalElements());
        paginatedProductResponse.setTotalPages(paginatedProductResponse.getTotalPages());
        paginatedProductResponse.setLast(paginatedProductResponse.isLast());

        return paginatedProductResponse;
    }
}
