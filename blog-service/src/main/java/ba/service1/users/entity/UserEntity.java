package ba.service1.users.entity;

import ba.service1.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "usr_user")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    @Column(name = "usr_id", unique = true, nullable = false, length = 255)
    private String keyCloakId;

    @Column(name = "usr_age",length = 500)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_gender")
    private Gender gender;

    @Column(name = "usr_bio",length = 500)
    private  String bio;

    @Column(name= "usr_profile_img",length = 500)
    private String profilePictureUrl;

    public static enum Gender {
        MALE,
        FEMALE
    }
}