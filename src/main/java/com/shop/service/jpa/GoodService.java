package com.shop.service.jpa;

import com.shop.dao.GoodDao;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.exception.RecordNotFoundException;
import com.shop.model.Category;
import com.shop.model.Good;
import com.shop.service.data.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {

    private GoodDao goodDao;

    private DataValidator dataValidator;

    @Autowired
    public void setGoodDao(GoodDao goodDao) {
        this.goodDao = goodDao;
        goodDao.setClazz(Good.class);
    }

    @Autowired
    public void setDataValidator(DataValidator validator){
        this.dataValidator = validator;
    }

    public void save(Good good) {
        // TODO: handle other fields
        if (dataValidator.isBlankText(good.getName())) {
            throw new InvalidDataException("Name of good can't be empty.");
        }

        if (goodDao.getByName(good.getName()) != null) {
            throw new RecordExistsException("Good by name [" + good.getName() + "] is already exists.");
        }

        goodDao.create(good);
    }

    public List<Category> findAll() {
        return goodDao.findAll();
    }

    public Good getById(long id) {
        Good result = null;

        if ((result = goodDao.findEntity(id)) == null) {
            throw new RecordNotFoundException();
        }

        return result;
    }

    public void deleteById(long id) {
        if (goodDao.findEntity(id) == null) {
            throw new RecordNotFoundException();
        }

        goodDao.deleteById(id);
    }

    public void update(Good good) {
        // TODO: handle other fields
        if (dataValidator.isBlankText(good.getName())) {
            throw new InvalidDataException("Name of good can't be empty.");
        }

        Good foundGood = goodDao.getByName(good.getName());
        if (foundGood != null && foundGood.getId() != good.getId()) {
            throw new RecordExistsException("Good by name [" + good.getName() + "] is already exists.");
        }

        goodDao.update(good);
    }
}
