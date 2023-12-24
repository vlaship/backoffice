package vlaship.backoffice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityUrlsConfig {

    static final String[] WHITE_URLS = {
            // OpenAPI
            "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            // Actuator
            "/actuator/**",
            // Login
            "/api/auth/**"
    };

    @Bean
    public List<String> whiteUrls() {
        return Arrays.stream(WHITE_URLS)
                .map(url -> url.replace("/**", ""))
                .toList();
    }
}
