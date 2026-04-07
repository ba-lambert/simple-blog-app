package ba.blog.users.dto;

import ba.blog.users.entity.UserEntity;
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
@Schema(description = "User profile response containing public profile information")
public class UserProfileResponse {
    
    @Schema(description = "Database ID of the user", example = "1")
    private String id;
    
    @Schema(description = "Keycloak user ID (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private String keyCloakId;
    
    @Schema(description = "User's age", example = "30")
    private Integer age;
    
    @Schema(description = "User's gender", example = "MALE")
    private String gender;
    
    @Schema(description = "User's biography", example = "Software Engineer passionate about microservices")
    private String bio;
    
    @Schema(description = "URL to user's profile picture", example = "https://example.com/images/avatar.jpg")
    private String profilePictureUrl;
    
    @Schema(description = "Profile creation timestamp", example = "2024-01-01T10:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Profile last update timestamp", example = "2024-01-15T14:30:00")
    private LocalDateTime updatedAt;

    public static UserProfileResponse fromEntity(UserEntity entity) {
        return UserProfileResponse.builder()
                .id(entity.getId())
                .keyCloakId(entity.getKeyCloakId())
                .age(entity.getAge())
                .gender(entity.getGender() != null ? entity.getGender().name() : null)
                .bio(entity.getBio())
                .profilePictureUrl(entity.getProfilePictureUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
