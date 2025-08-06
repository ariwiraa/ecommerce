package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.ReqCheckoutDTO;
import com.personal.ecommerce.model.dto.ResponseCheckoutDTO;

public interface OrderService {
    ResponseCheckoutDTO checkout(ReqCheckoutDTO req);
}
