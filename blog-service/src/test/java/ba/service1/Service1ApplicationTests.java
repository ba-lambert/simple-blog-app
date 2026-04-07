package ba.service1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8081/realms/test",
    "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8081/realms/test/protocol/openid-connect/certs"
})
class Service1ApplicationTests {

    @Test
    void contextLoads() {
    }

}
