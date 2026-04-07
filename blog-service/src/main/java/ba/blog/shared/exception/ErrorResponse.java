package ba.blog.shared.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response structure")
public class ErrorResponse {
    
    @Schema(description = "Timestamp when the error occurred", example = "2024-01-15T14:30:00")
    private LocalDateTime timestamp;
    
    @Schema(description = "HTTP status code", example = "400")
    private int status;
    
    @Schema(description = "Error type", example = "Bad Request")
    private String error;
    
    @Schema(description = "Detailed error message", example = "Invalid input data")
    private String message;
    
    @Schema(description = "Request path that caused the error", example = "/api/v1/profile/me")
    private String path;
}
