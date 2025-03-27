package com.personal.ecommerce.service.impl;

import com.personal.ecommerce.common.DateUtil;
import com.personal.ecommerce.config.JwtSecretConfig;
import com.personal.ecommerce.model.dto.UserInfo;
import com.personal.ecommerce.service.JwtService;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Autowired
    private JwtSecretConfig jwtSecretConfig;
    @Autowired
    private SecretKey secretKey;

    @Override
    public String generateToken(UserInfo userInfo) {
        LocalDateTime expirationTime = LocalDateTime.now().plus(jwtSecretConfig.getJwtExpirationTime());
        Date expiredDate = DateUtil.convertLocalDateTimeToDate(expirationTime);
        return Jwts.builder()
                .setSubject(userInfo.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();

            jwtParser.parseClaimsJwt(token);

            return true;
        } catch (Exception e) {
            log.error("Error validating token", e);
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        return jwtParser.parseClaimsJwt(token).getBody().getSubject();
    }
}
