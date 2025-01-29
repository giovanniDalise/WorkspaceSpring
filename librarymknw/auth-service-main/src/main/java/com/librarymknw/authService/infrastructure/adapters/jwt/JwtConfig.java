package com.librarymknw.authService.infrastructure.adapters.jwt;

import com.librarymknw.authService.core.ports.JwtPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    @Bean
    public JwtPort jwtPort() {
        return new JwtAdapter(jwtSecret, jwtExpirationMs);
    }
}