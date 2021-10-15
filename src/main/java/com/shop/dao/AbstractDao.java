package com.shop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractDao<T> {

    private Class<T> clazz;

    @Autowired
    protected SessionFactory sessionFactory;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findEntity(long id) {
        Session session = sessionFactory.openSession();
        T result = session.get(clazz, id);
        session.close();

        return result;
    }

    public List findAll() {
        Session session = sessionFactory.openSession();
        List resultList = session.createQuery("from " + clazz.getName()).list();
        session.close();

        return resultList;
    }

    public T create(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();

        return entity;
    }

    public void update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    public void delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    public void deleteById(long id) {
        T entity = findEntity(id);
        delete(entity);
    }
}
