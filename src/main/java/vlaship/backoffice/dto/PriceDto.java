package vlaship.backoffice.dto;

import lombok.Builder;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Builder
public record PriceDto(
        Long id,
        @Size(min = 3, max = 3, message = "Currency must be 3 characters")
        @NotNull
        String currency,
        @Positive(message = "Amount must be positive")
        @NotNull
        BigDecimal amount,
        Long productId
) implements Dto {
}
