package com.shop.dao.impl;

import com.shop.dao.AbstractDao;
import com.shop.dao.ManufacturerDao;
import com.shop.model.Manufacturer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ManufacturerDaoImpl extends AbstractDao<Manufacturer> implements ManufacturerDao {

    @Override
    public Manufacturer getByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Manufacturer m where m.name = :name");
        query.setParameter("name", name);

        try {
            return (Manufacturer) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } finally {
            session.close();
        }
    }
}
