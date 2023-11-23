package vlaship.backoffice.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
