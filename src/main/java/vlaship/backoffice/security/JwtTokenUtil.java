package vlaship.backoffice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vlaship.backoffice.exception.JwtAuthenticationException;

import java.io.Serializable;
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
        var exp = new Date(System.currentTimeMillis() + jwtProperties.getExpiration());
        var iat = new Date();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(iat)
                .expiration(exp)
                .signWith(jwtProperties.getSecretKey(), ALG)
                .compact();
    }

    public boolean validateToken(@NonNull String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtProperties.getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            switch (e) {
                case MalformedJwtException ex:
                    log.error("Invalid JWT token: {}", ex.getMessage());
                    break;
                case ExpiredJwtException ex:
                    log.error("JWT token is expired: {}", ex.getMessage());
                    break;
                case UnsupportedJwtException ex:
                    log.error("JWT token is unsupported: {}", ex.getMessage());
                    break;
                case IllegalArgumentException ex:
                    log.error("JWT claims string is empty: {}", ex.getMessage());
                    break;
                case SignatureException ex:
                    log.error("JWT signature does not match locally computed signature: {}", ex.getMessage());
                    break;
                default:
                    log.error("JWT token is invalid: {}", e.getMessage());
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
