package ba.blog.users.service;

import ba.blog.users.dto.KeycloakUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminService {
    
    private final Keycloak keycloak;
    
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakUserInfo getUserInfoById(String keycloakUserId) {
        try {
            UserRepresentation user = keycloak.realm(realm)
                    .users()
                    .get(keycloakUserId)
                    .toRepresentation();
            
            return mapToKeycloakUserInfo(user, keycloakUserId);
        } catch (Exception e) {
            log.error("Failed to fetch Keycloak user info for id: {}", keycloakUserId, e);
            throw new RuntimeException("Failed to fetch user from Keycloak", e);
        }
    }

    public KeycloakUserInfo getUserInfoByUsername(String username) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .search(username, true);
            
            if (users.isEmpty()) {
                throw new RuntimeException("User not found in Keycloak: " + username);
            }
            
            UserRepresentation user = users.get(0);
            return mapToKeycloakUserInfo(user, user.getId());
        } catch (Exception e) {
            log.error("Failed to fetch Keycloak user info for username: {}", username, e);
            throw new RuntimeException("Failed to fetch user from Keycloak", e);
        }
    }

    public List<KeycloakUserInfo> getAllUsers() {
        try {
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .list();
            
            return users.stream()
                    .map(user -> mapToKeycloakUserInfo(user, user.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch all users from Keycloak", e);
            throw new RuntimeException("Failed to fetch users from Keycloak", e);
        }
    }

    private KeycloakUserInfo mapToKeycloakUserInfo(UserRepresentation user, String userId) {
        List<String> realmRoles = getRealmRoles(userId);
        
        return KeycloakUserInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .emailVerified(user.isEmailVerified() != null && user.isEmailVerified())
                .enabled(user.isEnabled() != null && user.isEnabled())
                .createdTimestamp(user.getCreatedTimestamp())
                .realmRoles(realmRoles)
                .build();
    }

    private List<String> getRealmRoles(String userId) {
        try {
            return keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .listEffective()
                    .stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to fetch roles for user: {}", userId, e);
            return List.of();
        }
    }
}
