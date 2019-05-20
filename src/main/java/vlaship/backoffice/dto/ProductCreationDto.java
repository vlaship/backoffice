package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationDto implements Serializable {
    @NotNull
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    @NotNull
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @NotNull
    @NotEmpty(message = "Product's name must be not empty")
    private String name;
    @NotNull
    @Positive(message = "Category's ID must be positive")
    private Integer categoryId;
}
