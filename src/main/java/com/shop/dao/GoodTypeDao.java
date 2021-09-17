package com.shop.dao;

import com.shop.dao.GenericDao;
import com.shop.model.GoodType;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodTypeDao extends GenericDao<GoodType> {

    GoodType getByName(String name);
}
