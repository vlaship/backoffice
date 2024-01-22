package dev.vlaship.backoffice.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

@ConfigurationProperties(prefix = "client")
public record ClientProperties(
        String baseUrl,
        String path,
        Duration socketTimeout,
        Duration connectionTimeout,

        @NestedConfigurationProperty
        RetryConfiguration retry,

        @NestedConfigurationProperty
        PoolingConfiguration pooling
) {
}

record RetryConfiguration(
        int count,
        int interval
) {
}

record PoolingConfiguration(
        int maxTotal,
        int defaultMaxPerRoute
) {
}
