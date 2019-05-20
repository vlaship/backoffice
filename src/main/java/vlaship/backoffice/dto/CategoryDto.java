package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto implements IDto {
    private Integer id;
    @NotNull
    @NotEmpty(message = "Category's name must be not empty")
    private String name;
    private List<Integer> products;
    private Integer parentId;
    private List<Integer> subCategories;
}
