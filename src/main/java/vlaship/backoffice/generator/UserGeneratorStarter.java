package vlaship.backoffice.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("user")
public class UserGeneratorStarter implements GeneratorStarter {

    private final UserGenerator userGenerator;

    @Autowired
    public UserGeneratorStarter(final UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
    }

    @Override
    public void generate() {
        userGenerator.login();
    }

}
