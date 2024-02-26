package dev.vlaship.backoffice.controller;

import org.springframework.http.ResponseEntity;
import dev.vlaship.backoffice.dto.Dto;
import dev.vlaship.backoffice.facade.Facade;
import dev.vlaship.backoffice.model.Model;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AbstractController<M extends Model, D extends Dto> {

    private final Facade<M, D> facade;

    public ResponseEntity<D> update(D dto) {
        return ResponseEntity.accepted().body(facade.update(dto));
    }

    public ResponseEntity<Void> delete(Long id) {
        facade.delete(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<D> find(Long id) {
        return ResponseEntity.ok(facade.find(id));
    }

    public ResponseEntity<List<D>> findAll(Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    public AbstractController(final Facade<M, D> facade) {
        this.facade = facade;
    }

}
