package com.increff.store.controller;

import com.increff.store.dto.ReportDto;
import com.increff.store.dto.RevenueDto;
import com.increff.store.model.*;
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
public class RevenueApiController {

    @Autowired
    private RevenueDto dto;
    @Autowired
    private ReportDto reportDto;

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/admin/revenue-product", method = RequestMethod.POST)
    public List<ProductRevenueData> get_revenue_product(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.getProductWiseReport(form);
    }

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/admin/revenue-brand", method = RequestMethod.POST)
    public List<BrandRevenueData> getRevenueBrand(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.getBrandReport(form);
    }

    @ApiOperation(value = "Get Revenue on product items with date as filter")
    @RequestMapping(path = "/api/admin/revenue-category", method = RequestMethod.POST)
    public List<CategoryRevenueData> getRevenueCategory(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.getCategoryReport(form);
    }

    @ApiOperation(value = "Get Inventory Report")
    @RequestMapping(path = "/api/admin/inventory-report", method = RequestMethod.GET)
    public List<InventoryReportModel> getInventoryReport() throws ApiException
    {
        return dto.getInventoryReport();
    }

    @ApiOperation(value = "Get Daily Report Report")
    @RequestMapping(path = "/api/admin/daily-report", method = RequestMethod.GET)
    public List<DailyReportData> getDailyReport() throws ApiException
    {
        return reportDto.getAllDailyReport();
    }

}
