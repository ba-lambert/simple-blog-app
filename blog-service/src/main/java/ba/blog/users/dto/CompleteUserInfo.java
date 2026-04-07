package ba.blog.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Complete user information combining Keycloak authentication data with local profile data")
public class CompleteUserInfo {
    
    @Schema(description = "Keycloak user information including roles and account status")
    private KeycloakUserInfo keycloakInfo;
    
    @Schema(description = "Local database profile information (null if user hasn't created a profile yet)")
    private LocalUserProfile localProfile;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "User profile information stored in local database")
    public static class LocalUserProfile {
        
        @Schema(description = "Database ID", example = "1")
        private String id;
        
        @Schema(description = "Keycloak user ID (foreign key)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        private String keyCloakId;
        
        @Schema(description = "User's age", example = "30")
        private Integer age;
        
        @Schema(description = "User's gender", example = "MALE")
        private String gender;
        
        @Schema(description = "User's biography", example = "Software Engineer")
        private String bio;
        
        @Schema(description = "Profile picture URL", example = "https://example.com/avatar.jpg")
        private String profilePictureUrl;
        
        @Schema(description = "Profile creation timestamp", example = "2024-01-01T10:00:00")
        private LocalDateTime createdAt;
        
        @Schema(description = "Profile last update timestamp", example = "2024-01-15T14:30:00")
        private LocalDateTime updatedAt;
    }
}
