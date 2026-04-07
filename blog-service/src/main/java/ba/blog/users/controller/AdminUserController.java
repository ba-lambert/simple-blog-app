package ba.blog.users.controller;

import ba.blog.users.dto.CompleteUserInfo;
import ba.blog.users.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - User Management", description = "Administrative endpoints for managing users (ADMIN role required)")
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {
    
    private final AdminUserService adminUserService;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = """
                    Retrieves complete information for all users in the system.
                    Includes both Keycloak data (username, email, roles) and local database profile information.
                    
                    **Required Role:** ADMIN
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompleteUserInfo.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User does not have ADMIN role"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error - Failed to retrieve users from Keycloak or database"
            )
    })
    public ResponseEntity<List<CompleteUserInfo>> getAllUsers() {
        List<CompleteUserInfo> users = adminUserService.getAllUsersCompleteInfo();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/username/{username}")
    @Operation(
            summary = "Get user by username",
            description = """
                    Retrieves complete information for a specific user by their username.
                    Combines Keycloak user data with local database profile.
                    
                    **Required Role:** ADMIN
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CompleteUserInfo.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User does not have ADMIN role"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - User with specified username does not exist"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error - Failed to retrieve user from Keycloak"
            )
    })
    public ResponseEntity<CompleteUserInfo> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve", example = "john.doe", required = true)
            @PathVariable String username) {
        CompleteUserInfo userInfo = adminUserService.getCompleteUserInfoByUsername(username);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/id/{keycloakUserId}")
    @Operation(
            summary = "Get user by Keycloak ID",
            description = """
                    Retrieves complete information for a specific user by their Keycloak user ID.
                    Combines Keycloak user data with local database profile.
                    
                    **Required Role:** ADMIN
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CompleteUserInfo.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User does not have ADMIN role"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - User with specified ID does not exist"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error - Failed to retrieve user from Keycloak"
            )
    })
    public ResponseEntity<CompleteUserInfo> getUserById(
            @Parameter(description = "Keycloak user ID (UUID format)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479", required = true)
            @PathVariable String keycloakUserId) {
        CompleteUserInfo userInfo = adminUserService.getCompleteUserInfoById(keycloakUserId);
        return ResponseEntity.ok(userInfo);
    }
}
