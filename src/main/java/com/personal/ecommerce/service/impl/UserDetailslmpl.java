package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.model.dto.UserInfo;
import com.personal.ecommerce.model.entity.RoleEntity;
import com.personal.ecommerce.model.entity.UserEntity;
import com.personal.ecommerce.repository.RoleRepository;
import com.personal.ecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailslmpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<RoleEntity> roles = roleRepository.findByUserId(user.getUserId());

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setRoles(roles);

        return userInfo;
    }
}
