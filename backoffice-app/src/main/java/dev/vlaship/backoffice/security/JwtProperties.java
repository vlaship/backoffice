package dev.vlaship.backoffice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JwtProperties(
        String secret,
        long expirationSeconds
) {
}
