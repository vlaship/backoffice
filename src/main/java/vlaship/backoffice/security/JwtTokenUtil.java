package vlaship.backoffice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private final JwtProperties jwtProperties;
    private static final SignatureAlgorithm ALG = SignatureAlgorithm.HS512;

    public String generateToken(Authentication authentication) {
        var claims = new HashMap<String, Object>();
        return doGenerateToken(claims, authentication.getName());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSecretKey(), ALG)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private boolean isTokenExpired(String token) {
        var expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

}

//package vlaship.backoffice.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.MacAlgorithm;
//import io.jsonwebtoken.security.SecureDigestAlgorithm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtTokenUtil implements Serializable {
//
//    private static final MacAlgorithm ALG = Jwts.SIG.HS512;
//
//    private final JwtProperties jwtProperties;
//
//    public String generateToken(Authentication authentication) {
//        var claims = new HashMap<String, Object>();
//        return doGenerateToken(claims, authentication.getName());
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .claims(claims)
//                .subject(subject)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
//                .signWith(getSecretKey(), ALG)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(getSecretKey())
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty: {}", e.getMessage());
//        }
//
//        return false;
//    }
//
//    private boolean isTokenExpired(String token) {
//        var expiration = extractExpiration(token);
//        return expiration.before(new Date());
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        var claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(getSecretKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    private SecretKey getSecretKey() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
//    }
//
//}
