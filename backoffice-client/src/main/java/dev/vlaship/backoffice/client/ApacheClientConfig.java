package dev.vlaship.backoffice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Profile("apache")
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ApacheClientProperties.class)
public class ApacheClientConfig {

    @Bean("clientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(ApacheClientProperties clientProperties) {
        var connectionManager = new PoolingHttpClientConnectionManager();
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.of(clientProperties.connectionTimeout()))
                .setSocketTimeout(Timeout.of(clientProperties.socketTimeout()))
                .build();
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setMaxTotal(clientProperties.pooling().maxTotal());
        connectionManager.setDefaultMaxPerRoute(clientProperties.pooling().defaultMaxPerRoute());
        return connectionManager;
    }

    @Bean("clientObjectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean("clientRetryStrategy")
    public HttpRequestRetryStrategy httpRequestRetryStrategy(ApacheClientProperties clientProperties) {
        return new DefaultHttpRequestRetryStrategy(
                clientProperties.retry().count(),
                TimeValue.ofMilliseconds(clientProperties.retry().interval())
        );
    }

    @Bean("clientRequestFactory")
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Qualifier("clientConnectionManager") PoolingHttpClientConnectionManager connectionManager,
            @Qualifier("clientRetryStrategy") HttpRequestRetryStrategy retryStrategy
    ) {
        var client = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setRetryStrategy(retryStrategy)
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean("clientRestTemplate")
    public RestTemplate restTemplate(
            @Qualifier("clientObjectMapper") ObjectMapper objectMapper,
            @Qualifier("clientRequestFactory") ClientHttpRequestFactory clientHttpRequestFactory,
            RestTemplateBuilder restTemplateBuilder
    ) {
        return restTemplateBuilder
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .errorHandler(new ClientResponseErrorHandler())
                .requestFactory(() -> clientHttpRequestFactory)
                .build();
    }
}
