package vlaship.backoffice.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Builder
public record CategoryDto(
        Long id,
        @NotNull
        @NotEmpty(message = "Category's name must be not empty")
        String name,
        List<Long> products,
        Long parentId,
        List<Long> subCategories
) implements Dto {
}
