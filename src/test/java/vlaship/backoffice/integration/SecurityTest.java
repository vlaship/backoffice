package vlaship.backoffice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@AutoConfigureMockMvc
@SpringBootTest
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_without_auth() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", password = "123")
    void test_with_auth() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("root")));
    }

}
