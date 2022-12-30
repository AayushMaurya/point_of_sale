package com.increff.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController{
    @RequestMapping(value = "/ui/brands", method = RequestMethod.GET)
    public ModelAndView brand() {
        return mav("brands.html");
    }

    @RequestMapping(value = "/ui/inventory", method = RequestMethod.GET)
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/products", method = RequestMethod.GET)
    public ModelAndView product() {
        return mav("products.html");
    }
}
