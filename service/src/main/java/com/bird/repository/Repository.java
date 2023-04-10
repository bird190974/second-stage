package com.bird.repository;

import com.bird.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends Serializable, E extends BaseEntity<T>> {

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    Optional<E> findById(T id);

    List<E> findAll();
}
