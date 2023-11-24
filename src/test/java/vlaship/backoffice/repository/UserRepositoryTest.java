package vlaship.backoffice.repository;

import org.junit.jupiter.api.Test;
import vlaship.backoffice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
class UserRepositoryTest {

    private static final String username = "test";

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        repository.save(new User(username, ""));
    }

    @Test
    void test_findByName() {
        final User user = repository.findByName(username).orElseThrow();
        then(user.getName()).isEqualTo(username);

        then(repository.findByName("123").isPresent()).isFalse();
    }

}
