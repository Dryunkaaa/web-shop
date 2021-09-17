package com.shop.controller.admin;

import com.shop.model.Good;
import com.shop.service.jpa.GoodService;
import com.shop.service.jpa.GoodTypeService;
import com.shop.service.jpa.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/good")
public class GoodController {

    private GoodService goodService;
    private GoodTypeService goodTypeService;
    private ManufacturerService manufacturerService;

    @Autowired
    public void setGoodService(GoodService goodService) {
        this.goodService = goodService;
    }

    @Autowired
    public void setGoodTypeService(GoodTypeService goodTypeService) {
        this.goodTypeService = goodTypeService;
    }

    @Autowired
    public void setManufacturerService(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("goods", goodService.findAll());
        return "admin/good/goods";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("goodTypes", goodTypeService.findAll());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "admin/good/good_adding";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Good good,
                              @RequestParam long goodTypeId,
                              @RequestParam long manufacturerId) {
        good.setGoodType(goodTypeService.getById(goodTypeId));
        good.setManufacturer(manufacturerService.getById(manufacturerId));
        goodService.save(good);

        return "redirect:/admin/good/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        goodService.deleteById(id);
        return "redirect:/admin/good/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        model.addAttribute("error", error);
        model.addAttribute("good", goodService.getById(id));
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("goodTypes", goodTypeService.findAll());

        return "admin/good/good_editing";
    }

    @PostMapping("/edit")
    public String submitEditing(@ModelAttribute Good good,
                                @RequestParam long goodTypeId,
                                @RequestParam long manufacturerId) {

        good.setManufacturer(manufacturerService.getById(manufacturerId));
        good.setGoodType(goodTypeService.getById(goodTypeId));

        goodService.update(good);
        return "redirect:/admin/good/list";
    }
}
