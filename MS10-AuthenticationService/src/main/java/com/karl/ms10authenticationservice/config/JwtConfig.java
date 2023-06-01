package com.karl.ms10authenticationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${application.jwt}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

}
