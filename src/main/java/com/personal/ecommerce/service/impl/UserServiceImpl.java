package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.common.errors.ConflictException;
import com.personal.ecommerce.common.errors.ResourceNotFoundException;
import com.personal.ecommerce.enums.Role;
import com.personal.ecommerce.model.dto.UserRegisterRequest;
import com.personal.ecommerce.model.dto.UserResponse;
import com.personal.ecommerce.model.entity.RoleEntity;
import com.personal.ecommerce.model.entity.UserEntity;
import com.personal.ecommerce.model.entity.UserRoleEntity;
import com.personal.ecommerce.repository.RoleRepository;
import com.personal.ecommerce.repository.UserRepository;
import com.personal.ecommerce.repository.UserRoleRepository;
import com.personal.ecommerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Registers a new user to the system.
     *
     * @param request the registration request.
     * @return a user response containing the registered user information.
     * @throws ConflictException if the username or email already exists.
     * @throws ResourceNotFoundException if the role cannot be found.
     */
    @Override
    public UserResponse register(UserRegisterRequest request) {
        log.info("[UserService - register] started");

        log.debug("[UserService - register] request: {}", request);
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);
        log.info("[UserService - register] user: {}", user);

        RoleEntity role = roleRepository.findByName(String.valueOf(Role.ROLE_USER)).orElseThrow(() -> new
                ResourceNotFoundException("Role not found"));

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setId(new UserRoleEntity.UserRoleId(user.getUserId(), role.getRoleId()));
        userRoleRepository.save(userRole);

        log.info("[UserService - register] ended");
        return UserResponse.fromUserAndRoles(user, List.of(String.valueOf(Role.ROLE_USER)));
    }

    @Override
    public UserResponse findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<RoleEntity> roles = roleRepository.findByUserId(userId);

        return UserResponse.fromUserAndRoles(user, roles.stream().map(RoleEntity::getName).toList());
    }

    @Override
    public UserResponse findByUsernameOrEmail(String usernameOrEmail) {
        UserEntity user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<RoleEntity> roles = roleRepository.findByUserId(user.getUserId());

        return UserResponse.fromUserAndRoles(user, roles.stream().map(RoleEntity::getName).toList());
    }
}
