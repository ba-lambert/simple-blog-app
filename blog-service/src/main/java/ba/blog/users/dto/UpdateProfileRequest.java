package ba.blog.users.dto;

import ba.blog.users.entity.UserEntity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request body for updating user profile information")
public class UpdateProfileRequest {
    
    @Schema(description = "User's age", example = "30", minimum = "13", maximum = "120")
    private Integer age;
    
    @Schema(description = "User's biography or about section", example = "Software Engineer passionate about microservices", maxLength = 500)
    private String bio;
    
    @Schema(description = "User's gender", example = "MALE", allowableValues = {"MALE", "FEMALE"})
    private Gender gender;
    
    @Schema(description = "URL to user's profile picture", example = "https://example.com/images/avatar.jpg", maxLength = 500)
    private String profilePictureUrl;
}
