package com.epam.theater.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */


public interface BaseMethods<T> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T findById(Long id);

    void delete(Long id);

    void save(T t);

    void update(T t);

    void saveList(List<T> elementList);

}
