package com.increff.store.controller;

import com.increff.store.dto.RevenueDto;
import com.increff.store.model.BrandRevenueData;
import com.increff.store.model.CategoryRevenueData;
import com.increff.store.model.DateFilterForm;
import com.increff.store.model.ProductRevenueData;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class RevenuApiController {

    @Autowired
    private RevenueDto dto;

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/revenue-product", method = RequestMethod.POST)
    public List<ProductRevenueData> get_revenue_product(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.get_revenue_product(form);
    }

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/revenue-brand", method = RequestMethod.POST)
    public List<BrandRevenueData> get_revenue_brand(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.get_revenue_brand(form);
    }

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/revenue-category", method = RequestMethod.POST)
    public List<CategoryRevenueData> get_revenue_category(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.get_revenue_category(form);
    }
}
