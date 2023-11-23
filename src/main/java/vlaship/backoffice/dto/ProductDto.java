package vlaship.backoffice.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Builder
public record ProductDto(
        Long id,
        @NotNull
        @NotEmpty(message = "Product's name must be not empty")
        String name,
        List<Long> prices,
        List<Long> categories
) implements Dto {
}
