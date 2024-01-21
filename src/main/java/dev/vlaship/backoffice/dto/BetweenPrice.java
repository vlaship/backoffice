package dev.vlaship.backoffice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record BetweenPrice(
        @NotNull
        @Positive(message = "Amount must be positive")
        BigDecimal from,
        @NotNull
        @Positive(message = "Amount must be positive")
        BigDecimal to,
        @NotNull
        @Size(min = 3, max = 3, message = "Currency must be 3 characters")
        String currency
) implements Serializable {
}
