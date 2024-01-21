package dev.vlaship.backoffice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.io.Serializable;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties implements Serializable {

    private String secret;
    private long expirationSeconds;
    private SecretKey secretKey;

}
