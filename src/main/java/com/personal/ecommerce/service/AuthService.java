package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.AuthRequest;
import com.personal.ecommerce.model.dto.UserInfo;

public interface AuthService {
    public UserInfo authentication(AuthRequest authRequest);
}
