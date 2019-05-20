package vlaship.backoffice.facade;

import vlaship.backoffice.dto.IDto;
import vlaship.backoffice.model.IModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Facade<M extends IModel, D extends IDto> {

    D find(Integer id);

    List<D> findAll(Pageable pageable);

    D update(D dto);

    void delete(Integer id);

    M get(Integer id);

//    M get(D d);
}
