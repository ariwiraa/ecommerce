package com.personal.ecommerce.repository;

import com.personal.ecommerce.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    
    // Find order items by order ID
    List<OrderItemEntity> findByOrderId(Long orderId);
    
    // Find order items by product ID
    List<OrderItemEntity> findByProductId(Long productId);
    
    // Find order items by order ID and product ID
    List<OrderItemEntity> findByOrderIdAndProductId(Long orderId, Long productId);
    
    // Custom query to find order items with order details
    @Query(value = """
            SELECT oi.*
            FROM order_items oi
            JOIN orders o ON oi.order_id = o.id
            WHERE o.user_id = :userId
            ORDER BY oi.created_at DESC
            """, nativeQuery = true)
    List<OrderItemEntity> findOrderItemsByUserId(@Param("userId") Long userId);
    
    // Delete all order items by order ID
    @Modifying
    @Query(value = "DELETE FROM order_items WHERE order_id = :orderId", nativeQuery = true)
    void deleteAllByOrderId(@Param("orderId") Long orderId);
    
    // Count order items by order ID
    long countByOrderId(Long orderId);
    
    // Find top selling products
    @Query(value = """
            SELECT oi.product_id, SUM(oi.quantity) as total_quantity
            FROM order_items oi
            JOIN orders o ON oi.order_id = o.id
            WHERE o.order_status = 'DELIVERED'
            GROUP BY oi.product_id
            ORDER BY total_quantity DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Object[]> findTopSellingProducts(@Param("limit") int limit);
}