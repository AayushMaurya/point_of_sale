package com.increff.store.controller;

import com.increff.store.model.data.InfoData;
import com.increff.store.util.SecurityUtil;
import com.increff.store.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractUiController {
    @Autowired
    private InfoData info;

    @Value("${app.baseUrl}")
    private String baseUrl;

    protected ModelAndView mav (String page)
    {
        // Get current user
        UserPrincipal principal = SecurityUtil.getPrincipal();

        info.setEmail(principal == null ? "" : principal.getEmail());
        info.setRole(principal == null ? "" : principal.getRole());

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("info", info);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

    protected ModelAndView mav (String page, Integer orderId)
    {
        // Get current user
        UserPrincipal principal = SecurityUtil.getPrincipal();

        info.setEmail(principal == null ? "" : principal.getEmail());
        info.setRole(principal == null ? "" : principal.getRole());

        ModelAndView mav = new ModelAndView(page);

        mav.addObject("info", info);
        mav.addObject("orderId", orderId);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }

}
