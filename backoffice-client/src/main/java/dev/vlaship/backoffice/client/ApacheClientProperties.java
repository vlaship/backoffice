package dev.vlaship.backoffice.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

@ConfigurationProperties(prefix = "client.apache-http-client")
public record ApacheClientProperties(
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
