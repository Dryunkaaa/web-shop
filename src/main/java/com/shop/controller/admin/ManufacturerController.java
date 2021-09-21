package com.shop.controller.admin;

import com.shop.model.Manufacturer;
import com.shop.service.jpa.ManufacturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/manufacturer")
public class ManufacturerController {

    private Logger logger = LoggerFactory.getLogger(ManufacturerController.class);
    private ManufacturerService manufacturerService;

    @Autowired
    public void setManufacturerService(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        logger.info("Get list of manufacturers.");
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "admin/manufacturer/manufacturers";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute(name = "error") String error, Model model) {
        logger.info("Adding a manufacturer.");
        model.addAttribute("error", error);
        return "admin/manufacturer/manufacturer_adding";
    }

    @PostMapping("/add")
    public String doAdd(@RequestParam(name = "name") String name) {
        logger.info("Try to add a manufacturer by name - [{}]", name);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturerService.save(manufacturer);

        return "redirect:/admin/manufacturer/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "id") long id) {
        logger.info("Remove manufacturer by id - {}", id);
        manufacturerService.deleteById(id);
        return "redirect:/admin/manufacturer/list";
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "error") String error,
                       @RequestParam(name = "id") long id,
                       Model model) {
        logger.info("Edit manufacturer by id - {}", id);
        model.addAttribute("error", error);
        model.addAttribute("manufacturer", manufacturerService.getById(id));
        return "admin/manufacturer/manufacturer_editing";
    }

    @PostMapping("/edit")
    public String doEdit(@ModelAttribute Manufacturer editingManufacturer) {
        logger.info("Try to update editingManufacturer with new name [{}].", editingManufacturer.getName());
        manufacturerService.update(editingManufacturer);
        return "redirect:/admin/manufacturer/list";
    }
}
