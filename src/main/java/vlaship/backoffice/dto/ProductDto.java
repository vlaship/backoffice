package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Dto {

	private Integer id;

	@NotNull
	@NotEmpty(message = "Product's name must be not empty")
	private String name;

	private List<Integer> prices;

	private List<Integer> categories;

}
