package ba.blog.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Test", description = "Endpoints to test authentication and role extraction")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class AuthTestController {

    @GetMapping("/me")
    @Operation(
            summary = "Get current authentication details",
            description = "Returns the current user's authentication information including extracted roles. " +
                    "Use this endpoint to verify JWT token is being processed correctly."
    )
    public ResponseEntity<Map<String, Object>> getCurrentAuth(Authentication authentication) {
        log.info("Authentication request - Principal: {}", authentication.getName());
        log.info("Authorities: {}", authentication.getAuthorities());
        
        Map<String, Object> response = new HashMap<>();
        response.put("username", authentication.getName());
        response.put("authenticated", authentication.isAuthenticated());
        
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        
        response.put("roles", roles);
        response.put("hasAdminRole", roles.contains("ROLE_ADMIN"));
        response.put("hasUserRole", roles.contains("ROLE_USER"));
        
        log.info("Extracted roles: {}", roles);
        log.info("Has ADMIN role: {}", roles.contains("ROLE_ADMIN"));
        
        return ResponseEntity.ok(response);
    }
}
