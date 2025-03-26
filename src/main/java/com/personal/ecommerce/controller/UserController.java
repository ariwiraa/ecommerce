package com.personal.ecommerce.controller;

import com.personal.ecommerce.model.dto.BaseResponse;
import com.personal.ecommerce.model.dto.UserRegisterRequest;
import com.personal.ecommerce.model.dto.UserResponse;
import com.personal.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody  UserRegisterRequest request) {
        UserResponse userResponse = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success("success register user", userResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> findByUserId(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.findByUserId(userId);

        return ResponseEntity.ok(BaseResponse.success("success get user", userResponse));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<UserResponse>> findByUsernameOrEmail(@RequestParam("usernameOrEmail")
                                                                                String usernameOrEmail) {
        UserResponse userResponse = userService.findByUsernameOrEmail(usernameOrEmail);

        return ResponseEntity.ok(BaseResponse.success("success get user", userResponse));
    }
}
