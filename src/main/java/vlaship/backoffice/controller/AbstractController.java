package vlaship.backoffice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import vlaship.backoffice.dto.Dto;
import vlaship.backoffice.facade.Facade;
import vlaship.backoffice.model.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
public class AbstractController<M extends Model, D extends Dto> {

    private final Facade<M, D> facade;

    @PutMapping("/update")
    public ResponseEntity<D> update(@Valid final @RequestBody D dto) {
        return ResponseEntity.accepted().body(facade.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        facade.delete(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> find(final @PathVariable("id") Long id) {
        return ResponseEntity.ok(facade.find(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<D>> findAll(final Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    public AbstractController(final Facade<M, D> facade) {
        this.facade = facade;
    }

}
