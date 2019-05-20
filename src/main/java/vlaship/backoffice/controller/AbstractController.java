package vlaship.backoffice.controller;

import vlaship.backoffice.dto.IDto;
import vlaship.backoffice.facade.Facade;
import vlaship.backoffice.model.IModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class AbstractController<M extends IModel, D extends IDto> {

    private final Facade<M, D> facade;

    @PutMapping("/update")
    public D update(@Valid final @RequestBody D dto) {
        return facade.update(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void delete(final @PathVariable("id") Integer id) {
        facade.delete(id);
    }

    @GetMapping("/{id}")
    public D find(final @PathVariable("id") Integer id) {
        return facade.find(id);
    }

    @GetMapping("/list")
    public List<D> findAll(final Pageable pageable) {
        return facade.findAll(pageable);
    }

    public AbstractController(final Facade<M, D> facade) {
        this.facade = facade;
    }

}
