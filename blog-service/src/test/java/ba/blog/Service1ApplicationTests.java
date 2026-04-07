package ba.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;
import org.keycloak.admin.client.Keycloak;

import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8081/realms/test",
    "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8081/realms/test/protocol/openid-connect/certs",
    "keycloak.server-url=http://localhost:8081",
    "keycloak.realm=test",
    "keycloak.admin-username=admin",
    "keycloak.admin-password=admin",
    "keycloak.admin-client-id=admin-cli"
})
class Service1ApplicationTests {

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public Keycloak keycloakMock() {
            return mock(Keycloak.class);
        }
    }

    @Test
    void contextLoads() {
    }

}
