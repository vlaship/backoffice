package dev.vlaship.backoffice.controller.impl;

import dev.vlaship.backoffice.api.CategoryApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.controller.AbstractController;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.facade.impl.CategoryFacade;
import dev.vlaship.backoffice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class CategoryController extends AbstractController<Category, CategoryDto> implements CategoryApi {

    private final CategoryFacade categoryFacade;

    @Override
    public ResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        var dto = categoryFacade.create(categoryDto);
        return ResponseEntity.created(URI.create("/category/" + dto.id())).body(dto);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> find(String name, Pageable pageable) {
        return ResponseEntity.ok(categoryFacade.findAll(name, pageable));
    }

    public CategoryController(final CategoryFacade categoryFacade) {
        super(categoryFacade);
        this.categoryFacade = categoryFacade;
    }

}
