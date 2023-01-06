package com.increff.store.controller;

import com.increff.store.model.InfoData;
import com.increff.store.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipal;

@Controller
public abstract class AbstractUiController {
    @Autowired
    private InfoData info;

    @Value("${app.baseUrl}")
    private String baseUrl;

    protected ModelAndView mav (String page)
    {
        // Get current user
//        UserPrincipal principal = SecurityUtil.getPrincipal();

//        info.setEmail(principal == null ? "" : principal.getEmail());

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("info", info);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

    protected ModelAndView mav (String page, OrderData orderData)
    {
        // Get current user
//        UserPrincipal principal = SecurityUtil.getPrincipal();

//        info.setEmail(principal == null ? "" : principal.getEmail());

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("info", info);
        mav.addObject("orderId", orderData.getId());
        mav.addObject("orderCode", orderData.getOrderCode());
        mav.addObject("customerName", orderData.getCustomerName());
        mav.addObject("status", orderData.getStatus());
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
