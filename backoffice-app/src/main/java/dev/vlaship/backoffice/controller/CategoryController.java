package dev.vlaship.backoffice.controller;

import dev.vlaship.backoffice.api.CategoryApi;
import dev.vlaship.backoffice.dto.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.facade.CategoryFacade;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryFacade facade;

    @Override
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto) {
        var dto = facade.create(categoryDto);
        return ResponseEntity.created(URI.create("/category/" + dto.getId())).body(dto);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getCategoryByName(String name, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(name, pageable));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getCategories(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(CategoryDto dto) {
        return ResponseEntity.accepted().body(facade.update(dto));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }
}
