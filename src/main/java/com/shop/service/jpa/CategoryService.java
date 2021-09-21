package com.shop.service.jpa;

import com.shop.dao.CategoryDao;
import com.shop.exception.InvalidDataException;
import com.shop.exception.RecordExistsException;
import com.shop.exception.RecordNotFoundException;
import com.shop.model.Category;
import com.shop.service.data.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private CategoryDao categoryDao;
    private DataValidator dataValidator;

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        categoryDao.setClazz(Category.class);
    }

    @Autowired
    public void setDataValidator(DataValidator validator) {
        this.dataValidator = validator;
    }

    public void save(Category category) {
        if (dataValidator.isBlankText(category.getName())) {
            logger.warn("Trying to save category with blank name. Name - [{}]", category.getName());
            throw new InvalidDataException("Name of category can't be empty.");
        }

        if (categoryDao.getByName(category.getName()) != null) {
            logger.warn("Trying to save duplicate of category by name [{}].", category.getName());
            throw new RecordExistsException("Category by name [" + category.getName() + "] is already exists.");
        }

        categoryDao.create(category);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category getById(long id) {
        Category result = null;

        if ((result = categoryDao.findEntity(id)) == null) {
            logger.warn("Impossible to find a category by id [{}].", id);
            throw new RecordNotFoundException();
        }

        return result;
    }

    public void deleteById(long id) {
        if (categoryDao.findEntity(id) == null) {
            logger.warn("Impossible to delete a category by id [{}]. Not found.", id);
            throw new RecordNotFoundException();
        }

        categoryDao.deleteById(id);
    }

    public void update(Category category) {
        if (dataValidator.isBlankText(category.getName())) {
            logger.warn("Trying to save category with blank name during updating. Name - [{}]", category.getName());
            throw new InvalidDataException("Name of category can't be empty.");
        }

        Category foundCategory = categoryDao.getByName(category.getName());
        if (foundCategory != null && foundCategory.getId() != category.getId()) {
            logger.warn("Trying to save duplicate of category by name [{}] during updating.", category.getName());
            throw new RecordExistsException("Category by name [" + category.getName() + "] is already exists.");
        }

        categoryDao.update(category);
    }
}
