package vlaship.backoffice.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("all")
public class CatalogAndUserGeneratorStarter implements GeneratorStarter {

    private final UserGenerator userGenerator;
    private final CatalogGenerator catalogGenerator;

    @Autowired
    public CatalogAndUserGeneratorStarter(final UserGenerator userGenerator,
                                          final CatalogGenerator catalogGenerator) {
        this.userGenerator = userGenerator;
        this.catalogGenerator = catalogGenerator;
    }

    @Override
    public void generate() {
        userGenerator.login();
        catalogGenerator.catalog();
    }

}
