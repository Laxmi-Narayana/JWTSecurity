package com.lgellu.JwtSecurity.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key;
    private final long jwtExpiration;
    private final JwtParser parser;

    public JwtService(JwtProperties jwtProperties) {
        byte[] decodedKey = Base64.getDecoder().decode(jwtProperties.getSecret());
        if(decodedKey.length < 32) {
            throw new IllegalArgumentException("JWT Secret must be atleast 256 bits (32 bytes) after Base64 decoding");
        }
        this.key = Keys.hmacShaKeyFor(decodedKey);
        this.jwtExpiration = jwtProperties.getExpirationMs();
        this.parser = Jwts.parser()
                .verifyWith(key)
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtExpiration)))
                .signWith(key)
                .compact();
    }

    // Validate & Parse Token
    private Jws<Claims> validateToken(String token) {
        try {
            return parser.parseSignedClaims(token);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid or expired JWT token", e);
        }
    }

    public String extractUsername(String token) {
        return validateToken(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = validateToken(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }

}
