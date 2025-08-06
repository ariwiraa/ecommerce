package com.personal.ecommerce.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCheckoutDTO {
    private Long cartId;
    private List<Long> productIds;
    private String address;
    private String phoneNumber;
    private String paymentMethod;
}
