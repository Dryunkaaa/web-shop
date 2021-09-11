package com.shop.controller.admin;

import com.shop.dao.GenericDao;
import com.shop.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manufacturer")
public class ManufacturerController {

    private GenericDao<Manufacturer> manufacturerDao;

    @Autowired
    public void setManufacturerDao(GenericDao<Manufacturer> manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
        manufacturerDao.setClazz(Manufacturer.class);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("manufacturers", manufacturerDao.findAll());

        return "admin/manufacturer/manufacturers";
    }

    @GetMapping("/add")
    public String add() {
        return "admin/manufacturer/add-manufacturer";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam(name = "manufacturerName") String manufacturerName) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerName);
        manufacturerDao.create(manufacturer);

        return "redirect:/admin/manufacturer/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "manufacturerId") long manufacturerId) {
        manufacturerDao.deleteById(manufacturerId);
        return "redirect:/admin/manufacturer/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "manufacturerId") long manufacturerId, Model model) {
        model.addAttribute("manufacturer", manufacturerDao.findEntity(manufacturerId));
        return "admin/manufacturer/edit-manufacturer";
    }

    @PostMapping("/edit")
    public String submitEditing(
            @RequestParam(name = "manufacturerName") String manufacturerName,
            @RequestParam(name = "manufacturerId") long manufacturerId) {

        Manufacturer manufacturer = manufacturerDao.findEntity(manufacturerId);
        manufacturer.setName(manufacturerName);

        manufacturerDao.update(manufacturer);

        return "redirect:/admin/manufacturer/list";
    }
}
