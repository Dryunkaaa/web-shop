package com.shop.controller.admin;

import com.shop.model.GoodType;
import com.shop.service.jpa.CategoryService;
import com.shop.service.jpa.GoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/good-type")
public class GoodTypeController {

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
        model.addAttribute("goodTypes", goodTypeService.findAll());
        return "admin/good-type/good-types";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/good-type/good_type_adding";
    }

    @PostMapping("/add")
    public String addGoodType(@RequestParam(name = "categoryId") Long categoryId, @RequestParam(name = "name") String goodTypeName) {
        GoodType goodType = new GoodType();
        goodType.setCategory(categoryService.getById(categoryId));
        goodType.setName(goodTypeName);

        goodTypeService.save(goodType);

        return "redirect:/admin/good-type/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        goodTypeService.deleteById(id);
        return "redirect:/admin/good-type/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        model.addAttribute("error", error);
        model.addAttribute("goodType", goodTypeService.getById(id));
        model.addAttribute("categories", categoryService.findAll());

        return "admin/good-type/good_type_editing";
    }

    @PostMapping("/edit")
    public String submitEditing(@ModelAttribute GoodType goodType,
                                @RequestParam(name = "categoryId") long categoryId) {
        goodType.setCategory(categoryService.getById(categoryId));
        goodTypeService.update(goodType);

        return "redirect:/admin/good-type/list";
    }
}
