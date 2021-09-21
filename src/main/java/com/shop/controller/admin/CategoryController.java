package com.shop.controller.admin;

import com.shop.model.Category;
import com.shop.service.jpa.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/category/")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        logger.info("Get list of categories.");
        model.addAttribute("categories", categoryService.findAll());
        return "admin/category/categories";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        logger.info("Adding a category.");
        model.addAttribute("error", error);
        return "admin/category/category_adding";
    }

    @PostMapping("/add")
    public String doAdd(@RequestParam(name = "name") String name) {
        logger.info("Try to add a category by name - [{}]", name);
        Category category = new Category();
        category.setName(name);
        categoryService.save(category);

        return "redirect:/admin/category/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        logger.info("Remove category by id - {}", id);
        categoryService.deleteById(id);
        return "redirect:/admin/category/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        logger.info("Edit category by id - {}", id);
        model.addAttribute("error", error);
        model.addAttribute("category", categoryService.getById(id));
        return "admin/category/category_editing";
    }

    @PostMapping("/edit")
    public String doEdit(@ModelAttribute Category editingCategory) {
        logger.info("Try to update category with new name [{}].", editingCategory.getName());
        categoryService.update(editingCategory);
        return "redirect:/admin/category/list";
    }
}
