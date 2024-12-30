package com.AlTaraf.Booking.service.base;

import com.AlTaraf.Booking.database.entity.base.BaseEntity;
import com.AlTaraf.Booking.database.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<ID extends Serializable, E extends BaseEntity<ID>> {

    public abstract BaseRepository<ID, E> getRepository();

    public Page< E> listAll(int page, int size) {
        return getRepository().findAll(PageRequest.of(page, size));
    }


    @Transactional
    public E createEntity(E entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public void createEntity(List<E> entity) {
        getRepository().saveAll(entity);
    }


    @Transactional
    public E updateEntity(E entity) {
        return getRepository().save(entity);
    }

    public boolean hardDeleteEntity(E entity) {
        getRepository().delete(entity);
        return true;
    }

    public boolean hardDeleteEntity(List<E> entity) {
        getRepository().deleteAll(entity);
        return true;
    }

    public E findById(ID entityId) {
        Optional<E> optional = getRepository().findById(entityId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
    public E findReferenceById(ID entityId) {
        return getRepository().getReferenceById(entityId);
    }



}
