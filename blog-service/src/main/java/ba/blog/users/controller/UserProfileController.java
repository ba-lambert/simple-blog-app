package ba.blog.users.controller;

import ba.blog.users.dto.UpdateProfileRequest;
import ba.blog.users.dto.UserProfileResponse;
import ba.blog.users.entity.UserEntity;
import ba.blog.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for managing authenticated user's profile")
@SecurityRequirement(name = "bearerAuth")
public class UserProfileController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            summary = "Get current user profile",
            description = "Retrieves the profile information of the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token"
            )
    })
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        UserEntity user = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(UserProfileResponse.fromEntity(user));
    }

    @PutMapping("/me")
    @Operation(
            summary = "Update current user profile",
            description = "Updates the profile information of the currently authenticated user. Only provided fields will be updated."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile updated successfully",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token"
            )
    })
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Profile update data. All fields are optional.",
                    required = true
            )
            @RequestBody UpdateProfileRequest request) {
        UserEntity updatedUser = userService.updateUserProfile(authentication, request);
        return ResponseEntity.ok(UserProfileResponse.fromEntity(updatedUser));
    }
}
