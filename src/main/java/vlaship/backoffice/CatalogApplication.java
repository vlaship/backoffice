package vlaship.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class CatalogApplication {
    public static void main(final String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }
}
