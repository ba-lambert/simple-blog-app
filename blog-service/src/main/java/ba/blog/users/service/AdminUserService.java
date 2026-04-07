package ba.blog.users.service;

import ba.blog.users.dto.CompleteUserInfo;
import ba.blog.users.dto.KeycloakUserInfo;
import ba.blog.users.entity.UserEntity;
import ba.blog.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CompleteUserInfo getCompleteUserInfoByUsername(String username) {
        KeycloakUserInfo keycloakInfo = keycloakAdminService.getUserInfoByUsername(username);
        Optional<UserEntity> localUser = userRepository.findByKeyCloakId(keycloakInfo.getId());
        
        return CompleteUserInfo.builder()
                .keycloakInfo(keycloakInfo)
                .localProfile(localUser.map(this::mapToLocalProfile).orElse(null))
                .build();
    }

    @Transactional(readOnly = true)
    public CompleteUserInfo getCompleteUserInfoById(String keycloakUserId) {
        KeycloakUserInfo keycloakInfo = keycloakAdminService.getUserInfoById(keycloakUserId);
        Optional<UserEntity> localUser = userRepository.findByKeyCloakId(keycloakUserId);
        
        return CompleteUserInfo.builder()
                .keycloakInfo(keycloakInfo)
                .localProfile(localUser.map(this::mapToLocalProfile).orElse(null))
                .build();
    }

    @Transactional(readOnly = true)
    public List<CompleteUserInfo> getAllUsersCompleteInfo() {
        List<KeycloakUserInfo> keycloakUsers = keycloakAdminService.getAllUsers();
        
        return keycloakUsers.stream()
                .map(keycloakInfo -> {
                    Optional<UserEntity> localUser = userRepository.findByKeyCloakId(keycloakInfo.getId());
                    return CompleteUserInfo.builder()
                            .keycloakInfo(keycloakInfo)
                            .localProfile(localUser.map(this::mapToLocalProfile).orElse(null))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private CompleteUserInfo.LocalUserProfile mapToLocalProfile(UserEntity entity) {
        return CompleteUserInfo.LocalUserProfile.builder()
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
