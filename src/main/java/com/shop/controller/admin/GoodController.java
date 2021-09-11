package com.shop.controller.admin;

import com.shop.dao.GenericDao;
import com.shop.model.Good;
import com.shop.model.GoodType;
import com.shop.model.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/good")
public class GoodController {

    private GenericDao<Good> goodDao;
    private GenericDao<GoodType> goodTypeDao;
    private GenericDao<Manufacturer> manufacturerDao;

    @Autowired
    public void setGoodDao(GenericDao<Good> goodDao) {
        this.goodDao = goodDao;
        goodDao.setClazz(Good.class);
    }

    @Autowired
    public void setGoodTypeDao(GenericDao<GoodType> goodTypeDao) {
        this.goodTypeDao = goodTypeDao;
        goodTypeDao.setClazz(GoodType.class);
    }

    @Autowired
    public void setManufacturerDao(GenericDao<Manufacturer> manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
        manufacturerDao.setClazz(Manufacturer.class);
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("goods", goodDao.findAll());

        return "admin/good/goods";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("goodTypes", goodTypeDao.findAll());
        model.addAttribute("manufacturers", manufacturerDao.findAll());
        return "admin/good/add-good";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String name, @RequestParam Long price,
                              @RequestParam String model, @RequestParam long goodTypeId,
                              @RequestParam long manufacturerId) {
        Good good = new Good();
        good.setName(name);
        good.setPrice(price);
        good.setModel(model);
        good.setGoodType(goodTypeDao.findEntity(goodTypeId));
        good.setManufacturer(manufacturerDao.findEntity(manufacturerId));
        goodDao.create(good);

        return "redirect:/admin/good/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "goodId") long goodId) {
        goodDao.deleteById(goodId);
        return "redirect:/admin/good/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "goodId") long goodId, Model model) {
        model.addAttribute("good", goodDao.findEntity(goodId));
        model.addAttribute("manufacturers", manufacturerDao.findAll());
        model.addAttribute("goodTypes", goodTypeDao.findAll());

        return "admin/good/edit-good";
    }

    @PostMapping("/edit")
    public String submitEditing(@RequestParam String name, @RequestParam Long price,
            @RequestParam String model, @RequestParam long goodTypeId,
            @RequestParam long manufacturerId,
            @RequestParam(name = "goodId") long goodId) {

        Good good = goodDao.findEntity(goodId);
        good.setName(name);
        good.setPrice(price);
        good.setModel(model);
        good.setManufacturer(manufacturerDao.findEntity(manufacturerId));
        good.setGoodType(goodTypeDao.findEntity(goodTypeId));

        goodDao.update(good);

        return "redirect:/admin/good/list";
    }
}
