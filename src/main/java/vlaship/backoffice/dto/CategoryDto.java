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
public class CategoryDto implements Dto {

	private Integer id;

	@NotNull
	@NotEmpty(message = "Category's name must be not empty")
	private String name;

	private List<Integer> products;

	private Integer parentId;

	private List<Integer> subCategories;

}
