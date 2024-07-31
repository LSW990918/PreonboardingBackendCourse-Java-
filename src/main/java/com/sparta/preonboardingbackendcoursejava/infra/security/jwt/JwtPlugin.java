package com.sparta.preonboardingbackendcoursejava.infra.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtPlugin {

    private final String issuer;
    private final String secretKey;
    private final long accessTokenExpirationHour;

    public JwtPlugin(
            @Value("${auth.jwt.issuer}") String issuer,
            @Value("${auth.jwt.secretKey}") String secretKey,
            @Value("${auth.jwt.accessTokenExpirationHour}") long accessTokenExpirationHour) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.accessTokenExpirationHour = accessTokenExpirationHour;
    }

    public Jws<Claims> validateToken(String jwt) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                    .build()
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String generateAccessToken(String subject, String nickname) {
        return generateToken(subject, nickname, Duration.ofHours(accessTokenExpirationHour));
    }

    private String generateToken(String subject, String nickname, Duration expiration) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("nickname", nickname);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(java.time.Instant.now().plus(expiration)))
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }
}
