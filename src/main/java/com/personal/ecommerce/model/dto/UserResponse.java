package com.personal.ecommerce.model.dto;

import com.personal.ecommerce.model.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private Boolean enabled;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<String> roles;

    public static UserResponse fromUserAndRoles(UserEntity user, List<String> roles) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setEnabled(user.isEnabled());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setRoles(roles);
        return response;
    }
}