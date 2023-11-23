package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
