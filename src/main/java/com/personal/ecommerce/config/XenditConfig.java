package com.personal.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xendit.Xendit;
import com.xendit.XenditClient;

@Configuration
public class XenditConfig {

    @Value("${xendit.secret-key}")
    private String secretKey;

    @Value("${xendit.public-key}")
    private String publicKey;

    @Bean
    public Xendit xenditClient() {
        Xendit.apiKey = secretKey;
        return new Xendit();
    }
    
}
