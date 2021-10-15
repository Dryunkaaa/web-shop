package com.shop.controller.admin;

import com.shop.model.Category;
import com.shop.service.jpa.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(@RequestBody String id) {
        logger.info("Remove category by id - {}", id);
        categoryService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>("Removed.", HttpStatus.OK);
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
