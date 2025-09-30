package com.lgellu.JwtSecurity.service.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JwtProperties {

    @NotBlank(message = "JWT secret cannot be blank")
    // Ensures only Base64 chars allowed (A–Z, a–z, 0–9, +, /, =)
    @Pattern(
            regexp = "^[A-Za-z0-9+/=]+$",
            message = "JWT secret must be a valid Base64 string"
    )
    private String secret;

    @Min(value = 60000, message = "Expiration must be at least 1 minute (60000 ms)")
    private long expirationMs;


}
