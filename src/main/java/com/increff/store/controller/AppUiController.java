package com.increff.store.controller;

import com.increff.store.dto.OrderDto;
import com.increff.store.model.OrderData;
import com.increff.store.service.ApiException;
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

    @RequestMapping(value = "/ui/order-item/{orderId}", method = RequestMethod.GET)
    public ModelAndView orderItemById(@PathVariable Integer orderId) throws ApiException {
//        OrderData d = orderDto.getOrderById(orderId);
        return mav("orderItem.html", orderId);
    }

    @RequestMapping(value = "/ui/revenue", method = RequestMethod.GET)
    public ModelAndView orderItemById() throws ApiException {
        return mav("revenue.html");
    }

    @RequestMapping(value = "/ui/inventory-report", method = RequestMethod.GET)
    public ModelAndView inventoryReport() throws ApiException {
        return mav("inventoryReport.html");
    }

    @RequestMapping(value = "/ui/home", method = RequestMethod.GET)
    public ModelAndView home() throws ApiException {
        return mav("home.html");
    }
}
