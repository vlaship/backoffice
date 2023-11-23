package vlaship.backoffice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
