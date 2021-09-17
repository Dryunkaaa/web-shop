package com.shop.dao.impl;

import com.shop.dao.AbstractDao;
import com.shop.dao.GoodTypeDao;
import com.shop.model.GoodType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GoodTypeDaoImpl extends AbstractDao<GoodType> implements GoodTypeDao {

    @Override
    public GoodType getByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from GoodType gt where gt.name = :name");
        query.setParameter("name", name);

        try {
            return (GoodType) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } finally {
            session.close();
        }
    }
}
