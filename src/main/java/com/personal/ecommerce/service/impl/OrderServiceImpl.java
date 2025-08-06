package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.enums.OrderStatus;
import com.personal.ecommerce.enums.PaymentStatus;
import com.personal.ecommerce.model.dto.ReqCheckoutDTO;
import com.personal.ecommerce.model.dto.ResponseCheckoutDTO;
import com.personal.ecommerce.model.entity.CartEntity;
import com.personal.ecommerce.model.entity.CartItemEntity;
import com.personal.ecommerce.model.entity.OrderEntity;
import com.personal.ecommerce.model.entity.OrderItemEntity;
import com.personal.ecommerce.model.entity.PaymentEntity;
import com.personal.ecommerce.model.entity.ProductEntity;
import com.personal.ecommerce.repository.CartItemRepository;
import com.personal.ecommerce.repository.CartRepository;
import com.personal.ecommerce.repository.OrderItemRepository;
import com.personal.ecommerce.repository.OrderRepository;
import com.personal.ecommerce.repository.PaymentRepository;
import com.personal.ecommerce.repository.ProductRepository;
import com.personal.ecommerce.service.OrderService;
import com.personal.ecommerce.util.EnumConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseCheckoutDTO checkout(ReqCheckoutDTO req) {
        // Validate input
        if (req.getCartId() == null || req.getProductIds() == null || req.getProductIds().isEmpty()) {
            throw new IllegalArgumentException("Cart ID and Product IDs are required");
        }

        // Get cart and items in one query
        CartEntity cart = cartRepository.findByCartIdWithItems(req.getCartId(), req.getProductIds());
        List<CartItemEntity> cartItems = cart.getItems();
        
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }

        // Get products and convert to Map for O(1) lookup
        List<ProductEntity> productList = productRepository.findByProductIds(req.getProductIds());
        if (productList == null || productList.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Map<Long, ProductEntity> productMap = productList.stream()
                .collect(Collectors.toMap(ProductEntity::getProductId, product -> product));

        // Single loop for validation and calculation - O(n)
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();
        
        for (CartItemEntity cartItem : cartItems) {
            ProductEntity product = productMap.get(cartItem.getProductId());
            
            if (product == null) {
                throw new RuntimeException("Product not found: " + cartItem.getProductId());
            }
            
            // Stock validation
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            
            // Calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalAmount = totalAmount.add(subtotal);
            
            // Prepare order item (will be saved after order is created)
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setProductId(product.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerItem(product.getPrice());
            orderItem.setSubtotal(subtotal);
            orderItems.add(orderItem);
        }
        
        // Update product stock
        productRepository.saveAll(productList);
        
        // Create and save order
        OrderEntity order = createOrder(cart.getUserId(), totalAmount, req.getAddress());
        OrderEntity savedOrder = orderRepository.save(order);
        
        // Set order reference for all order items and save in batch
        orderItems.forEach(item -> item.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);
        
        log.info("Order created successfully: {} with {} items, total: {}", 
                savedOrder.getOrderNumber(), orderItems.size(), totalAmount);

        PaymentEntity payment = createPayment(savedOrder, totalAmount, req.getPaymentMethod());
        paymentRepository.save(payment);

        // Return response
        ResponseCheckoutDTO response = new ResponseCheckoutDTO();
        response.setOrderNumber(savedOrder.getOrderNumber());
        return response;
    }

    private PaymentEntity createPayment(OrderEntity order, BigDecimal totalAmount, String paymentMethod) {
        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(order);
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(EnumConverter.toPaymentMethod(paymentMethod));
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(generateTransactionId());
        return payment;
    }

    private OrderEntity createOrder(Long userId, BigDecimal totalAmount, String address) {
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setOrderNumber(generateOrderNumber());
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(address);
        order.setPaymentMethod("PENDING");
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateTransactionId() {
        return "TRX-" + System.currentTimeMillis() + "-" + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
}
