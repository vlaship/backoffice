package dev.vlaship.backoffice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@ConfigurationProperties("oauth2")
public record OAuth2Properties(
        @NestedConfigurationProperty
        RsaKey rsa,
        Duration expiration
) {
}

record RsaKey(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
