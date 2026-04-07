package ba.blog.users.service;

import ba.blog.users.dto.UpdateProfileRequest;
import ba.blog.users.entity.UserEntity;
import ba.blog.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String keyCloakId = jwt.getSubject();
        Optional<UserEntity> userEntity = userRepository.findByKeyCloakId(keyCloakId);
        if (userEntity.isPresent()) {
            return userEntity.get();
        } else {
            UserEntity newProfile = UserEntity.builder().keyCloakId(keyCloakId).build();
            return userRepository.save(newProfile);
        }
    }

    @Transactional
    public UserEntity updateUserProfile(Authentication authentication, UpdateProfileRequest request) {
        UserEntity user = getCurrentUser(authentication);
        applyProfileUpdates(user, request);
        return userRepository.save(user);
    }

    private void applyProfileUpdates(UserEntity user, UpdateProfileRequest request) {
        Optional.ofNullable(request.getAge()).ifPresent(user::setAge);
        Optional.ofNullable(request.getBio()).ifPresent(user::setBio);
        Optional.ofNullable(request.getGender()).ifPresent(user::setGender);
        Optional.ofNullable(request.getProfilePictureUrl()).ifPresent(user::setProfilePictureUrl);
    }
}
