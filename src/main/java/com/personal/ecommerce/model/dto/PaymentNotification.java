package com.personal.ecommerce.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentNotification { 
    private String id;
    private BigDecimal amount;
    private String status;
    private Instant created;
    private boolean isHigh;
    private Instant paidAt;
    private String userId;
    private String currency;
    private String paymentId;
    private String description;
    private String externalId;
    private BigDecimal paidAmount;
    private String payerEmail;
    private String ewalletType;
    private String merchantName;
    private String paymentChannel;
    private String paymentMethod;
    private String paymentMethodId;
}
