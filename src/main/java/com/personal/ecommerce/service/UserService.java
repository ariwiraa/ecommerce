package com.personal.ecommerce.service;

import com.personal.ecommerce.model.dto.UserRegisterRequest;
import com.personal.ecommerce.model.dto.UserResponse;
import org.springframework.data.jpa.repository.Query;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    UserResponse findByUserId(Long userId);
    UserResponse findByUsernameOrEmail(String usernameOrEmail);
}
