package com.personal.ecommerce.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCheckoutDTO {
    private String orderNumber;
    private String paymentUrl;
    private String paymentId;
    private BigDecimal amount;
    private String xenditInvoiceStatus;
    private String xenditInvoiceId;

}
