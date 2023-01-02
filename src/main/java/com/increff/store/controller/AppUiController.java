package com.increff.store.controller;

import com.increff.store.dto.OrderDto;
import com.increff.store.model.OrderData;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController{

    @Autowired
    OrderDto orderDto;
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

//    @RequestMapping(value = "/ui/order-item", method = RequestMethod.GET)
//    public ModelAndView orderItem() {
//        return mav("orderItem.html");
//    }

    @RequestMapping(value = "/ui/order-item/{id}", method = RequestMethod.GET)
    public ModelAndView orderItemById(@PathVariable int id) throws ApiException {
        OrderData d = orderDto.get_id(id);
        return mav("orderItem.html", d);
    }
}
