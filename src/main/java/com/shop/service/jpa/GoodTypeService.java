package com.shop.service.jpa;

import com.shop.dao.GoodTypeDao;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.exception.RecordNotFoundException;
import com.shop.model.GoodType;
import com.shop.service.data.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodTypeService {

    private GoodTypeDao goodTypeDao;
    private DataValidator dataValidator;
    private Logger logger = LoggerFactory.getLogger(GoodTypeService.class);


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
            logger.warn("Trying to save type of good with blank name. Name - [{}]", goodType.getName());
            throw new InvalidDataException("Name of type of good can't be empty.");
        }

        if (goodTypeDao.getByName(goodType.getName()) != null) {
            logger.warn("Trying to save duplicate of type of good by name [{}].", goodType.getName());
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
            logger.warn("Impossible to find a type of good by id [{}].", id);
            throw new RecordNotFoundException();
        }

        return result;
    }

    public void deleteById(long id) {
        if (goodTypeDao.findEntity(id) == null) {
            logger.warn("Impossible to delete a type of good by id [{}]. Not found.", id);
            throw new RecordNotFoundException();
        }

        goodTypeDao.deleteById(id);
    }

    public void update(GoodType goodType) {
        if (dataValidator.isBlankText(goodType.getName())) {
            logger.warn("Trying to save type of good with blank name during updating. Name - [{}]", goodType.getName());
            throw new InvalidDataException("Name of type of good can't be empty.");
        }

        GoodType foundGoodType = goodTypeDao.getByName(goodType.getName());
        if (foundGoodType != null && foundGoodType.getId() != goodType.getId()) {
            logger.warn("Trying to save duplicate of type of good by name [{}] during updating.", goodType.getName());
            throw new RecordExistsException("Type of good by name [" + goodType.getName() + "] is already exists.");
        }

        goodTypeDao.update(goodType);
    }
}
