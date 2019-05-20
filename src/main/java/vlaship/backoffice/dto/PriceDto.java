package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceDto implements IDto {
    private Integer id;
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    @NotNull
    private String currency;
    @Positive(message = "Amount must be positive")
    @NotNull
    private BigDecimal amount;
    private Integer productId;
}
