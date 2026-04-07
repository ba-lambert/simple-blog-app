package ba.service1.users.controller;

import ba.service1.users.dto.UpdateProfileRequest;
import ba.service1.users.dto.UserProfileResponse;
import ba.service1.users.entity.UserEntity;
import ba.service1.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        UserEntity user = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(UserProfileResponse.fromEntity(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        UserEntity updatedUser = userService.updateUserProfile(authentication, request);
        return ResponseEntity.ok(UserProfileResponse.fromEntity(updatedUser));
    }
}
