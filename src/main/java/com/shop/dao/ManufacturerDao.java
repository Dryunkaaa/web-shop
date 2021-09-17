package com.shop.dao;

import com.shop.dao.GenericDao;
import com.shop.model.Manufacturer;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerDao extends GenericDao<Manufacturer> {

    Manufacturer getByName(String name);
}
