package vlaship.backoffice.repository;

import vlaship.backoffice.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    private static final String username = "test";

    @Autowired
    private UserRepository repository;

    @Before
    public void setup() {
        repository.save(new User(username, ""));
    }

    @Test
    public void test_findByName() {
        final User user = repository.findByName(username).orElseThrow();
        then(user.getName()).isEqualTo(username);

        then(repository.findByName("123").isPresent()).isFalse();
    }
}