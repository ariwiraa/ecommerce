package com.personal.ecommerce.repository;

import com.personal.ecommerce.enums.OrderStatus;
import com.personal.ecommerce.enums.PaymentStatus;
import com.personal.ecommerce.model.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
    // Find orders by user ID
    List<OrderEntity> findByUserId(Long userId);
    
    // Find orders by user ID with pagination
    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);
    
    // Find order by order number
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
    
    // Check if order number exists
    boolean existsByOrderNumber(String orderNumber);
    
    // Find orders by payment status
    List<OrderEntity> findByPaymentStatus(PaymentStatus paymentStatus);
    
    // Find orders by order status
    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);
    
    // Find orders by user ID and order status
    List<OrderEntity> findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    
    // Find orders by user ID and payment status
    List<OrderEntity> findByUserIdAndPaymentStatus(Long userId, PaymentStatus paymentStatus);
    
    // Custom query to find orders with pagination and filtering
    @Query(value = """
            SELECT o.* FROM orders o
            WHERE (:userId IS NULL OR o.user_id = :userId)
            AND (:orderStatus IS NULL OR o.order_status = :orderStatus)
            AND (:paymentStatus IS NULL OR o.payment_status = :paymentStatus)
            ORDER BY o.created_at DESC
            """, nativeQuery = true)
    Page<OrderEntity> findOrdersWithFilters(
            @Param("userId") Long userId,
            @Param("orderStatus") String orderStatus,
            @Param("paymentStatus") String paymentStatus,
            Pageable pageable
    );
    
    // Find recent orders by user
    @Query(value = """
            SELECT o.* FROM orders o
            WHERE o.user_id = :userId
            ORDER BY o.created_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<OrderEntity> findRecentOrdersByUserId(@Param("userId") Long userId, @Param("limit") int limit);
}