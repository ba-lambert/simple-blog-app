package ba.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String oauth2SchemeName = "keycloak_oauth";

        return new OpenAPI()
                .info(new Info()
                        .title("Blog Service API")
                        .version("1.0.0")
                        .description("""
                                # Blog Application Microservice API
                                
                                This API provides endpoints for managing blog posts, user profiles, and administrative functions.
                                
                                ## Authentication
                                This API uses **Keycloak OAuth2** for authentication. To access protected endpoints:
                                1. Obtain a JWT token from Keycloak
                                2. Include the token in the `Authorization` header as `Bearer <token>`
                                
                                ## Roles
                                - **USER**: Standard user access to profile and content
                                - **ADMIN**: Administrative access to user management endpoints
                                
                                ## Base URL
                                - Development: `http://localhost:8080`
                                """)
                        .contact(new Contact()
                                .name("Blog Service Team")
                                .email("support@blogservice.com")
                                .url("https://blogservice.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token obtained from Keycloak"))
                        .addSecuritySchemes(oauth2SchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .password(new OAuthFlow()
                                                .tokenUrl(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                                                .refreshUrl(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")))))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)
                        .addList(oauth2SchemeName));
    }
}
