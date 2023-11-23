package vlaship.backoffice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetweenPrice implements Serializable {

	@NotNull
	@Positive(message = "Amount must be positive")
	private BigDecimal from;

	@NotNull
	@Positive(message = "Amount must be positive")
	private BigDecimal to;

	@NotNull
	@Size(min = 3, max = 3, message = "Currency must be 3 characters")
	private String currency;

}
