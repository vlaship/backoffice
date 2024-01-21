package dev.vlaship.backoffice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignupRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
