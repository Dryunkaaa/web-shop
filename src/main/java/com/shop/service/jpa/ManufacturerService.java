package com.shop.service.jpa;

import com.shop.dao.ManufacturerDao;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.exception.RecordNotFoundException;
import com.shop.model.Manufacturer;
import com.shop.service.data.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    private ManufacturerDao manufacturerDao;

    private DataValidator dataValidator;

    @Autowired
    public void setManufacturerDao(ManufacturerDao manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
        manufacturerDao.setClazz(Manufacturer.class);
    }

    @Autowired
    public void setDataValidator(DataValidator validator) {
        this.dataValidator = validator;
    }

    public void save(Manufacturer manufacturer) {
        if (dataValidator.isBlankText(manufacturer.getName())) {
            throw new InvalidDataException("Name of manufacturer can't be empty.");
        }

        if (manufacturerDao.getByName(manufacturer.getName()) != null) {
            throw new RecordExistsException("Manufacturer by name [" + manufacturer.getName() + "] is already exists.");
        }

        manufacturerDao.create(manufacturer);
    }

    public List<Manufacturer> findAll() {
        return manufacturerDao.findAll();
    }

    public Manufacturer getById(long id) {
        Manufacturer result = null;

        if ((result = manufacturerDao.findEntity(id)) == null) {
            throw new RecordNotFoundException();
        }

        return result;
    }

    public void deleteById(long id) {
        if (manufacturerDao.findEntity(id) == null) {
            throw new RecordNotFoundException();
        }

        manufacturerDao.deleteById(id);
    }

    public void update(Manufacturer manufacturer) {
        if (dataValidator.isBlankText(manufacturer.getName())) {
            throw new InvalidDataException("Name of category can't be empty.");
        }

        Manufacturer foundManufacturer = manufacturerDao.getByName(manufacturer.getName());
        if (foundManufacturer != null && foundManufacturer.getId() != manufacturer.getId()) {
            throw new RecordExistsException("Category by name [" + manufacturer.getName() + "] is already exists.");
        }

        manufacturerDao.update(manufacturer);
    }
}
