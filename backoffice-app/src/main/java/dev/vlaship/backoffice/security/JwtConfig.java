package dev.vlaship.backoffice.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean
    public SecretKey secretKey(JwtProperties jwtProperties) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
    }
}
