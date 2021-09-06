package com.shop.controller.admin;

import com.shop.dao.GenericDao;
import com.shop.model.Category;
import com.shop.model.GoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/good-type")
public class GoodTypeController {

    private GenericDao<Category> categoryDao;
    private GenericDao<GoodType> goodTypeDao;

    @Autowired
    public void setCategoryDao(GenericDao<Category> categoryDao) {
        this.categoryDao = categoryDao;
        categoryDao.setClazz(Category.class);
    }

    @Autowired
    public void setGoodTypeDao(GenericDao<GoodType> goodTypeDao) {
        this.goodTypeDao = goodTypeDao;
        goodTypeDao.setClazz(GoodType.class);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("goodTypes", goodTypeDao.findAll());

        return "good-types";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        return "add-good-type";
    }

    @PostMapping("/add")
    public String addGoodType(@RequestParam(name = "categoryId") Long categoryId, @RequestParam(name = "type-name") String goodTypeName) {
        GoodType goodType = new GoodType();
        goodType.setCategory(categoryDao.findEntity(categoryId));
        goodType.setName(goodTypeName);

        goodTypeDao.create(goodType);

        return "redirect:/admin/good-type/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "goodTypeId") long categoryId) {
        goodTypeDao.deleteById(categoryId);
        return "redirect:/admin/good-type/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "goodTypeId") long goodTypeId, Model model) {
        model.addAttribute("goodType", goodTypeDao.findEntity(goodTypeId));
        model.addAttribute("categories", categoryDao.findAll());

        return "edit-good-type";
    }

    @PostMapping("/edit")
    public String submitEditing(
            @RequestParam(name = "goodTypeName") String goodTypeName,
            @RequestParam(name = "goodTypeId") long goodTypeId,
            @RequestParam(name = "categoryId") long categoryId) {

        GoodType goodType = goodTypeDao.findEntity(goodTypeId);
        goodType.setName(goodTypeName);
        goodType.setCategory(categoryDao.findEntity(categoryId));

        goodTypeDao.update(goodType);

        return "redirect:/admin/good-type/list";
    }
}
