package ba.service1.users.dto;

import ba.service1.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String id;
    private String keyCloakId;
    private Integer age;
    private String gender;
    private String bio;
    private String profilePictureUrl;
    private LocalDateTime createdAt;
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
