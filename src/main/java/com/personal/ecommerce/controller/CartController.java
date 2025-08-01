package com.personal.ecommerce.controller;

import com.personal.ecommerce.model.dto.*;
import com.personal.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carts")
@SecurityRequirement(name = "Bearer")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<BaseResponse<Object>> addItem(@RequestBody AddToCartRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        cartService.addItemToCart(userInfo.getUser().getUserId(), request.getProductId(), request.getQuantity());

        return ResponseEntity.ok(BaseResponse.success("success add to cart", null));
    }

    @GetMapping("/items")
    public ResponseEntity<BaseResponse<List<CartItemResponse>>> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        List<CartItemResponse> cartItemResponseList = cartService.getCartItems(userInfo.getUser().getUserId());

        return ResponseEntity.ok(BaseResponse.success("success get cart", cartItemResponseList));
    }

    @PutMapping("/items")
    public ResponseEntity<BaseResponse<Object>> updateItem(@RequestBody UpdateCartItemRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        cartService.updateItemQuantity(userInfo.getUser().getUserId(), request.getProductId(), request.getQuantity());

        return ResponseEntity.ok(BaseResponse.success("update item quantity", null));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        cartService.removeItemFromCart(userInfo.getUser().getUserId(), id);

        return ResponseEntity.ok(BaseResponse.success("success delete item", null));
    }


}

