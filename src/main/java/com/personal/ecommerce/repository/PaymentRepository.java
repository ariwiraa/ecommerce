package com.personal.ecommerce.repository;

import com.personal.ecommerce.enums.PaymentMethod;
import com.personal.ecommerce.enums.PaymentStatus;
import com.personal.ecommerce.model.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    // Find payments by order ID
    List<PaymentEntity> findByOrderId(Long orderId);
    
    // Find payment by transaction ID
    Optional<PaymentEntity> findByTransactionId(String transactionId);
    
    // Check if transaction ID exists
    boolean existsByTransactionId(String transactionId);
    
    // Find payments by payment status
    List<PaymentEntity> findByPaymentStatus(PaymentStatus paymentStatus);
    
    // Find payments by payment method
    List<PaymentEntity> findByPaymentMethod(PaymentMethod paymentMethod);
    
    // Find payments by order ID and payment status
    List<PaymentEntity> findByOrderIdAndPaymentStatus(Long orderId, PaymentStatus paymentStatus);

    
    // Custom query to find payments with order details
    @Query(value = """
            SELECT p.*
            FROM payments p
            JOIN orders o ON p.order_id = o.id
            WHERE o.user_id = :userId
            ORDER BY p.created_at DESC
            """, nativeQuery = true)
    List<PaymentEntity> findPaymentsByUserId(@Param("userId") Long userId);
    
    // Find payments with pagination and filtering
    @Query(value = """
            SELECT p.* FROM payments p
            WHERE (:paymentStatus IS NULL OR p.payment_status = :paymentStatus)
            AND (:paymentMethod IS NULL OR p.payment_method = :paymentMethod)
            ORDER BY p.created_at DESC
            """, nativeQuery = true)
    Page<PaymentEntity> findPaymentsWithFilters(
            @Param("paymentStatus") String paymentStatus,
            @Param("paymentMethod") String paymentMethod,
            Pageable pageable
    );
    
    // Find pending payments older than specified minutes
    @Query(value = """
            SELECT p.* FROM payments p
            WHERE p.payment_status = 'PENDING'
            AND p.created_at < NOW() - (INTERVAL '1' MINUTE * :minutes)
            """, nativeQuery = true)
    List<PaymentEntity> findPendingPaymentsOlderThan(@Param("minutes") int minutes);
    
    // Count payments by status
    long countByPaymentStatus(PaymentStatus paymentStatus);
}