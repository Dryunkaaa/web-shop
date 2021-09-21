package com.shop.controller.admin;

import com.shop.model.GoodType;
import com.shop.service.jpa.CategoryService;
import com.shop.service.jpa.GoodTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/good-type")
public class GoodTypeController {

    private Logger logger = LoggerFactory.getLogger(GoodTypeController.class);
    private GoodTypeService goodTypeService;
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setGoodTypeService(GoodTypeService goodTypeService) {
        this.goodTypeService = goodTypeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        logger.info("Get list of types of good.");
        model.addAttribute("goodTypes", goodTypeService.findAll());
        return "admin/good-type/good-types";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        logger.info("Adding a type of good.");
        model.addAttribute("error", error);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/good-type/good_type_adding";
    }

    @PostMapping("/add")
    public String doAdd(@RequestParam(name = "categoryId") Long categoryId,
                        @RequestParam(name = "name") String goodTypeName) {
        logger.info("Try to add a type of good by name - [{}]", goodTypeName);
        GoodType goodType = new GoodType();
        goodType.setCategory(categoryService.getById(categoryId));
        goodType.setName(goodTypeName);

        goodTypeService.save(goodType);

        return "redirect:/admin/good-type/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        logger.info("Remove type of good by id - {}", id);
        goodTypeService.deleteById(id);
        return "redirect:/admin/good-type/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        logger.info("Edit type of good by id - {}", id);
        model.addAttribute("error", error);
        model.addAttribute("goodType", goodTypeService.getById(id));
        model.addAttribute("categories", categoryService.findAll());

        return "admin/good-type/good_type_editing";
    }

    @PostMapping("/edit")
    public String doEdit(@ModelAttribute GoodType editingGoodType,
                                @RequestParam(name = "categoryId") long categoryId) {
        logger.info("Try to update type of good with new name [{}].", editingGoodType.getName());
        editingGoodType.setCategory(categoryService.getById(categoryId));
        goodTypeService.update(editingGoodType);

        return "redirect:/admin/good-type/list";
    }
}
