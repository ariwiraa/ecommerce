package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.common.errors.UnauthorizedException;
import com.personal.ecommerce.model.dto.AuthRequest;
import com.personal.ecommerce.model.dto.UserInfo;
import com.personal.ecommerce.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserInfo authentication(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            return (UserInfo) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            log.error("Error authenticating user", e);
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}
