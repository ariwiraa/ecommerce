package com.personal.ecommerce.model.dto;

import com.personal.ecommerce.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private List<String> roles;
}
