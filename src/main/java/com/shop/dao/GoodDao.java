package com.shop.dao;

import com.shop.dao.GenericDao;
import com.shop.model.Category;
import com.shop.model.Good;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodDao extends GenericDao<Good> {

    Good getByName(String name);
}
