package com.shop.dao;

import com.shop.dao.GenericDao;
import com.shop.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends GenericDao<Category> {

    Category getByName(String name);
}
