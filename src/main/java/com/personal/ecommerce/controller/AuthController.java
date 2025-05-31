package com.personal.ecommerce.controller;

import com.personal.ecommerce.model.dto.*;
import com.personal.ecommerce.model.entity.RoleEntity;
import com.personal.ecommerce.service.AuthService;
import com.personal.ecommerce.service.JwtService;
import com.personal.ecommerce.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> authenticate(@RequestBody AuthRequest request) {
        UserInfo userInfo =  authService.authentication(request);
        String token = jwtService.generateToken(userInfo);
        AuthResponse authResponse = new AuthResponse(token, userInfo.getUsername(), userInfo.getRoles().stream().map(RoleEntity::getName).toList());

        return ResponseEntity.ok(BaseResponse.success("success login", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody  UserRegisterRequest request) {
        UserResponse userResponse = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success("success register user", userResponse));
    }
}
