package dev.vlaship.backoffice.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client")
public record ClientProperties(
        String baseUrl,
        String path
) {
}
