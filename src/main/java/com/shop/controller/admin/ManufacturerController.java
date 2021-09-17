package com.shop.controller.admin;

import com.shop.model.Manufacturer;
import com.shop.service.jpa.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/manufacturer")
public class ManufacturerController {

    private ManufacturerService manufacturerService;

    @Autowired
    public void setManufacturerService(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "admin/manufacturer/manufacturers";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        model.addAttribute("error", error);
        return "admin/manufacturer/manufacturer_adding";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam(name = "name") String name) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturerService.save(manufacturer);

        return "redirect:/admin/manufacturer/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        manufacturerService.deleteById(id);
        return "redirect:/admin/manufacturer/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        model.addAttribute("error", error);
        model.addAttribute("manufacturer", manufacturerService.getById(id));
        return "admin/manufacturer/manufacturer_editing";
    }

    @PostMapping("/edit")
    public String submitEditing(@ModelAttribute Manufacturer manufacturer) {
        manufacturerService.update(manufacturer);
        return "redirect:/admin/manufacturer/list";
    }
}
