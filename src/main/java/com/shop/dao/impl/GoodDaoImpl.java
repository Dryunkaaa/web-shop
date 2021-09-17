package com.shop.dao.impl;

import com.shop.dao.AbstractDao;
import com.shop.dao.GoodDao;
import com.shop.model.Good;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GoodDaoImpl extends AbstractDao<Good> implements GoodDao {

    @Override
    public Good getByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Good g where g.name = :name");
        query.setParameter("name", name);

        try {
            return (Good) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } finally {
            session.close();
        }
    }
}
