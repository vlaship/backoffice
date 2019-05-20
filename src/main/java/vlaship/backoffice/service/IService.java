package vlaship.backoffice.service;

import vlaship.backoffice.model.IModel;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService<M extends IModel> {

    M find(Integer id);

    List<M> findAll(Pageable pageable);

    void delete(M m);

    M save(M m);

    M get(Integer id);
}
