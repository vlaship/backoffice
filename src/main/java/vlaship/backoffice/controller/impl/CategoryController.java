package vlaship.backoffice.controller.impl;

import org.springframework.http.ResponseEntity;
import vlaship.backoffice.controller.AbstractController;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.facade.impl.CategoryFacade;
import vlaship.backoffice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractController<Category, CategoryDto> {

    private final CategoryFacade categoryFacade;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDto> create(@Valid final @RequestBody CategoryDto categoryDto) {
        var dto = categoryFacade.create(categoryDto);
        return ResponseEntity.created(URI.create("/category/" + dto.id())).body(dto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CategoryDto>> find(final @PathVariable("name") String name, final Pageable pageable) {
        return ResponseEntity.ok(categoryFacade.findAll(pageable, name));
    }

    public CategoryController(final CategoryFacade categoryFacade) {
        super(categoryFacade);
        this.categoryFacade = categoryFacade;
    }

}
