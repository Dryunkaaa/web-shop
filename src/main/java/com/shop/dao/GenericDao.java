package com.shop.dao;

import java.util.List;

public interface GenericDao<T> {

    void setClazz(Class<T> clazz);

    T findEntity(long id);

    List findAll();

    T create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(long id);
}
