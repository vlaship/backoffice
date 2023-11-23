package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto implements Dto {

	private Integer id;

	@Size(min = 3, max = 3, message = "Currency must be 3 characters")
	@NotNull
	private String currency;

	@Positive(message = "Amount must be positive")
	@NotNull
	private BigDecimal amount;

	private Integer productId;

}
