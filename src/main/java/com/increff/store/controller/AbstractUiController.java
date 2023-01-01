package com.increff.store.controller;

import com.increff.store.model.InfoData;
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

    protected ModelAndView mav (String page, int id)
    {
        // Get current user
//        UserPrincipal principal = SecurityUtil.getPrincipal();

//        info.setEmail(principal == null ? "" : principal.getEmail());

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("info", info);
        mav.addObject("orderId", id);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
