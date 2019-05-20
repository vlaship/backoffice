package vlaship.backoffice.controller.impl;

import vlaship.backoffice.controller.AbstractController;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.facade.impl.CategoryFacade;
import vlaship.backoffice.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends AbstractController<Category, CategoryDto> {

    private final CategoryFacade categoryFacade;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid final @RequestBody CategoryDto categoryDto) {
        return categoryFacade.create(categoryDto);
    }

    @GetMapping("/name/{name}")
    public List<CategoryDto> find(final @PathVariable("name") String name, final Pageable pageable) {
        return categoryFacade.findAll(pageable, name);
    }

    @Autowired
    public CategoryController(final CategoryFacade categoryFacade) {
        super(categoryFacade);
        this.categoryFacade = categoryFacade;
    }
}
