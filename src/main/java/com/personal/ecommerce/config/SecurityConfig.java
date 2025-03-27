package com.personal.ecommerce.config;

import com.personal.ecommerce.service.impl.UserDetailslmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailslmpl userDetailslmpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the {@link AuthenticationManager} which is the main entry point to the Spring Security
     * Authentication API. It is responsible for authenticating an {@link Authentication} object and
     * returning a fully populated object.
     *
     * @param authenticationConfiguration The configuration for the authentication process.
     * @return The fully configured and populated {@link AuthenticationManager}.
     * @throws Exception If an error occurs while creating the {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean for providing authentication by using the {@link UserDetailslmpl} for authentication and
     * the {@link BCryptPasswordEncoder} for encoding the passwords.
     *
     * @return A fully configured {@link DaoAuthenticationProvider} instance.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailslmpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
