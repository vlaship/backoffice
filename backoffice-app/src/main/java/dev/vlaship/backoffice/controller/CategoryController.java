package dev.vlaship.backoffice.controller;

import dev.vlaship.backoffice.api.CategoryApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.facade.impl.CategoryFacade;
import dev.vlaship.backoffice.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryFacade facade;

    @Override
    public ResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        var dto = facade.create(categoryDto);
        return ResponseEntity.created(URI.create("/category/" + dto.id())).body(dto);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> find(String name, Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(name, pageable));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @Override
    public ResponseEntity<CategoryDto> update(CategoryDto dto) {
        return ResponseEntity.accepted().body(facade.update(dto));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryDto> find(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }
}
