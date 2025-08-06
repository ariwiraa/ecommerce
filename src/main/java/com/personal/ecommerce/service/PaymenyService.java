package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.PaymentNotification;
import com.personal.ecommerce.model.dto.ReqCheckoutDTO;
import com.personal.ecommerce.model.dto.ResponseCheckoutDTO;

public interface PaymenyService {
    ResponseCheckoutDTO create(ReqCheckoutDTO req);
    ResponseCheckoutDTO getPayment(String paymentId);
    void handlerNotification(PaymentNotification notification);


    

}
