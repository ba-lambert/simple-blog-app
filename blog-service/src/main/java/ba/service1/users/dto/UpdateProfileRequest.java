package ba.service1.users.dto;

import ba.service1.users.entity.UserEntity.Gender;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private Integer age;
    private String bio;
    private Gender gender;
    private String profilePictureUrl;
}
