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

    @RequestMapping(value = "/ui/orders", method = RequestMethod.GET)
    public ModelAndView order() {
        return mav("orders.html");
    }

    @RequestMapping(value = "/ui/order-item", method = RequestMethod.GET)
    public ModelAndView orderItem() {
        return mav("orderItem.html");
    }
}
