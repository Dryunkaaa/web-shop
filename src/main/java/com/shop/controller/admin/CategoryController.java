package com.shop.controller.admin;

import com.shop.dao.GenericDao;
import com.shop.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/category/")
public class CategoryController {

    private GenericDao<Category> categoryDao;

    @Autowired
    public void setCategoryDao(GenericDao<Category> categoryDao) {
        this.categoryDao = categoryDao;
        categoryDao.setClazz(Category.class);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("categories", categoryDao.findAll());

        return "admin/category/categories";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/category/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam(name = "categoryName") String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryDao.create(category);

        return "redirect:/admin/category/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "categoryId") long categoryId) {
        categoryDao.deleteById(categoryId);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "categoryId") long categoryId, Model model) {
        model.addAttribute("category", categoryDao.findEntity(categoryId));
        return "admin/category/edit-category";
    }

    @PostMapping("/edit")
    public String submitEditing(
            @RequestParam(name = "categoryName") String categoryName,
            @RequestParam(name = "categoryId") long categoryId) {

        Category category = categoryDao.findEntity(categoryId);
        category.setName(categoryName);

        categoryDao.update(category);

        return "redirect:/admin/category/list";
    }
}
