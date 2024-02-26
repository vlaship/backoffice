package dev.vlaship.backoffice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(getCacheNames());

        cacheManager.registerCustomCache("cacheName1", caffeineCacheBuilder().expireAfterWrite(Duration.ofSeconds(10)).build());

        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder();
    }

    private Collection<String> getCacheNames() {
        return List.of("cacheName1", "cacheName2", "cacheName3");
    }

}
