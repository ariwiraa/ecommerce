package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.UserInfo;

public interface JwtService {
    String generateToken(UserInfo userInfo);

    boolean validateToken(String token);

    String getUsernameFromToken(String token);
}
