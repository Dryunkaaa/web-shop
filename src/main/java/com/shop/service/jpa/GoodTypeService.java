package com.shop.service.jpa;

import com.shop.dao.GoodTypeDao;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.exception.RecordNotFoundException;
import com.shop.model.GoodType;
import com.shop.service.data.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodTypeService {

    private GoodTypeDao goodTypeDao;
    private DataValidator dataValidator;

    @Autowired
    public void setGoodTypeDao(GoodTypeDao goodTypeDao) {
        this.goodTypeDao = goodTypeDao;
        goodTypeDao.setClazz(GoodType.class);
    }


    @Autowired
    public void setDataValidator(DataValidator validator) {
        this.dataValidator = validator;
    }

    public void save(GoodType goodType) {
        if (dataValidator.isBlankText(goodType.getName())) {
            throw new InvalidDataException("Name of type of good can't be empty.");
        }

        if (goodTypeDao.getByName(goodType.getName()) != null) {
            throw new RecordExistsException("Type of good by name [" + goodType.getName() + "] is already exists.");
        }

        goodTypeDao.create(goodType);
    }

    public List<GoodType> findAll() {
        return goodTypeDao.findAll();
    }

    public GoodType getById(long id) {
        GoodType result = null;

        if ((result = goodTypeDao.findEntity(id)) == null) {
            throw new RecordNotFoundException();
        }

        return result;
    }

    public void deleteById(long id) {
        if (goodTypeDao.findEntity(id) == null) {
            throw new RecordNotFoundException();
        }

        goodTypeDao.deleteById(id);
    }

    public void update(GoodType goodType) {
        if (dataValidator.isBlankText(goodType.getName())) {
            throw new InvalidDataException("Name of type of good can't be empty.");
        }

        GoodType foundGoodType = goodTypeDao.getByName(goodType.getName());
        if (foundGoodType != null && foundGoodType.getId() != goodType.getId()) {
            throw new RecordExistsException("Type of good by name [" + goodType.getName() + "] is already exists.");
        }

        goodTypeDao.update(goodType);
    }
}
