package ba.blog.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Keycloak user information including authentication and authorization details")
public class KeycloakUserInfo {
    
    @Schema(description = "Keycloak user ID (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private String id;
    
    @Schema(description = "Username", example = "john.doe")
    private String username;
    
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "First name", example = "John")
    private String firstName;
    
    @Schema(description = "Last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "Whether email has been verified", example = "true")
    private boolean emailVerified;
    
    @Schema(description = "Whether account is enabled", example = "true")
    private boolean enabled;
    
    @Schema(description = "Account creation timestamp (Unix timestamp in milliseconds)", example = "1677721600000")
    private Long createdTimestamp;
    
    @Schema(description = "List of realm-level roles assigned to the user", example = "[\"ADMIN\", \"USER\"]")
    private List<String> realmRoles;
    
    @Schema(description = "List of client-level roles assigned to the user", example = "[]")
    private List<String> clientRoles;
}
