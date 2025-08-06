package com.personal.ecommerce.util;

import com.personal.ecommerce.enums.PaymentMethod;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumConverter {
    
    // Cache untuk mapping string ke enum (case-insensitive)
    private static final Map<String, PaymentMethod> PAYMENT_METHOD_MAP = 
        Arrays.stream(PaymentMethod.values())
            .collect(Collectors.toMap(
                method -> method.name().toLowerCase(),
                method -> method
            ));
    
    public static PaymentMethod toPaymentMethod(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
        
        String normalizedValue = value.trim().toLowerCase().replace("-", "_").replace(" ", "_");
        PaymentMethod paymentMethod = PAYMENT_METHOD_MAP.get(normalizedValue);
        
        if (paymentMethod == null) {
            throw new IllegalArgumentException(
                String.format("Invalid payment method: '%s'. Valid options are: %s", 
                    value, 
                    Arrays.toString(PaymentMethod.values()))
            );
        }
        
        return paymentMethod;
    }
}