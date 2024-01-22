package dev.vlaship.backoffice.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {
}
