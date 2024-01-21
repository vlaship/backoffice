package dev.vlaship.backoffice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import dev.vlaship.backoffice.exception.JwtAuthenticationException;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private static final MacAlgorithm ALG = Jwts.SIG.HS512;

    private final JwtProperties jwtProperties;

    @NonNull
    public String generateToken(Authentication authentication) {
        return doGenerateToken(new HashMap<>(), authentication.getName());
    }

    @NonNull
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        var iat = Instant.now();
        var exp = iat.plusSeconds(jwtProperties.getExpirationSeconds());
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(iat))
                .expiration(Date.from(exp))
                .signWith(jwtProperties.getSecretKey(), ALG)
                .compact();
    }

    public void validateToken(@NonNull String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtProperties.getSecretKey())
                    .build()
                    .parseSignedClaims(token);

        } catch (JwtException | IllegalArgumentException e) {
            switch (e) {
                case MalformedJwtException ex -> log.error("Invalid JWT token: {}", ex.getMessage());
                case ExpiredJwtException ex -> log.error("JWT token is expired: {}", ex.getMessage());
                case UnsupportedJwtException ex -> log.error("JWT token is unsupported: {}", ex.getMessage());
                case IllegalArgumentException ex -> log.error("JWT claims string is empty: {}", ex.getMessage());
                case SignatureException ex -> log.error("JWT signature does not match locally computed signature: {}", ex.getMessage());
                default -> log.error("JWT token is invalid: {}", e.getMessage());
            }

            throw new JwtAuthenticationException();
        }
    }

    @NonNull
    public String extractUsername(@NonNull String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @NonNull
    public <T> T extractClaim(@NonNull String token, @NonNull Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @NonNull
    private Claims extractAllClaims(@NonNull String token) {
        return Jwts.parser()
                .verifyWith(jwtProperties.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
