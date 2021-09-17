package com.shop.dao.impl;

import com.shop.dao.AbstractDao;
import com.shop.dao.CategoryDao;
import com.shop.model.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {

    @Override
    public Category getByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Category c where c.name = :name");
        query.setParameter("name", name);

        try {
            return (Category) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } finally {
            session.close();
        }
    }
}
