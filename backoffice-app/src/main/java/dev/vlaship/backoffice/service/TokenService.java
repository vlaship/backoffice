package dev.vlaship.backoffice.service;

import dev.vlaship.backoffice.security.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final OAuth2Properties properties;

    @NonNull
    public Jwt generateToken(@NonNull String name) {
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(properties.expiration()))
                .subject(name)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }
}
