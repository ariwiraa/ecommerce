package com.personal.ecommerce.controller;

import com.personal.ecommerce.model.dto.BaseResponse;
import com.personal.ecommerce.model.dto.PaginatedProductResponse;
import com.personal.ecommerce.model.dto.ProductRequest;
import com.personal.ecommerce.model.dto.ProductResponse;
import com.personal.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/product")
@SecurityRequirement(name = "Bearer")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<BaseResponse<String>> createProduct(@RequestBody ProductRequest payload) {
        try {
            String response = productService.createProduct(payload);

            return ResponseEntity.ok(BaseResponse.success("success create product", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<ProductResponse>>> createProduct(@RequestParam String name) {
        try {
            List<ProductResponse> response = productService.getProductByName(name);

            return ResponseEntity.ok(BaseResponse.success("success create product", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("filter")
    public ResponseEntity<BaseResponse<PaginatedProductResponse>> createProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "product_id,asc") String[] sort
    ) {
        try {
            List<Sort.Order> orderList = new ArrayList<>();
            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orderList.add(new Sort.Order(sortByDirection(_sort[1]), _sort[0]));
                }
            } else {
                orderList.add(new Sort.Order(sortByDirection(sort[1]), sort[0]));
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by(orderList));

            PaginatedProductResponse response = productService.getByPage(pageable);

            return ResponseEntity.ok(BaseResponse.success("success create product", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(BaseResponse.error(e.getMessage()));
        }
    }

    private Sort.Direction sortByDirection(String sort) {
        if (sort.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        }

        return Sort.Direction.DESC;
    }
}
