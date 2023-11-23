package vlaship.backoffice.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record ProductCreationDto(
        @NotNull
        @Size(min = 3, max = 3, message = "Currency must be 3 characters")
        String currency,
        @NotNull
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull
        @NotEmpty(message = "Product's name must be not empty")
        String name,
        @NotNull
        @Positive(message = "Category's ID must be positive")
        Long categoryId
) implements Serializable {
}
