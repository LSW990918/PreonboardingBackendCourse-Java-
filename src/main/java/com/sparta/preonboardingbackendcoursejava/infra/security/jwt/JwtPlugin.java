package com.sparta.preonboardingbackendcoursejava.infra.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtPlugin {

    private final String issuer;
    private final String secretKey;
    private final long accessTokenExpirationHour;

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtPlugin(
            @Value("${auth.jwt.issuer}") String issuer,
            @Value("${auth.jwt.secretKey}") String secretKey,
            @Value("${auth.jwt.accessTokenExpirationHour}") long accessTokenExpirationHour) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.accessTokenExpirationHour = accessTokenExpirationHour;
        this.algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public Try<DecodedJWT> validateToken(String jwt) {
        return Try.of(() -> verifier.verify(jwt));
    }

    public String generateAccessToken(String subject, String nickname) {
        return generateToken(subject, nickname, Duration.ofHours(accessTokenExpirationHour));
    }

    private String generateToken(String subject, String nickname, Duration expiration) {
        return JWT.create()
                .withSubject(subject)
                .withClaim("nickname", nickname)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(expiration)))
                .sign(algorithm);
    }
}
