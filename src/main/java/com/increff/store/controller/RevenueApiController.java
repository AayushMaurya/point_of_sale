package com.increff.store.controller;

import com.increff.store.dto.ReportDto;
import com.increff.store.dto.RevenueDto;
import com.increff.store.model.*;
import com.increff.store.api.ApiException;
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

    @ApiOperation(value = "Get Revenue on product items with all filter")
    @RequestMapping(path = "/api/admin/sales-report", method = RequestMethod.POST)
    public List<ProductRevenueData> getSalesReport(@RequestBody FilterForm form) throws ApiException {
        return dto.getSalesReport(form);
    }

    @ApiOperation(value = "Get Inventory Report")
    @RequestMapping(path = "/api/admin/inventory-report", method = RequestMethod.GET)
    public List<InventoryReportModel> getInventoryReport() throws ApiException {
        return dto.getInventoryReport();
    }

    @ApiOperation(value = "Get Inventory Report by brand category filter")
    @RequestMapping(path = "/api/admin/inventory-report", method = RequestMethod.POST)
    public List<InventoryReportModel> getInventoryReport(@RequestBody BrandCategoryFilterForm form) throws ApiException {
        return dto.getInventoryReportWitDateFilter(form);
    }

    @ApiOperation(value = "Get Daily Report between given dated")
    @RequestMapping(path = "/api/admin/daily-report", method = RequestMethod.POST)
    public List<DailyReportData> getDailyReport(@RequestBody DateFilterForm form) throws ApiException {
        return reportDto.getAllDailyReport(form);
    }

    @ApiOperation(value = "Get all daily Report")
    @RequestMapping(path = "/api/admin/daily-report", method = RequestMethod.GET)
    public List<DailyReportData> getDailyReport() throws ApiException {
        return reportDto.getAllDailyReport();
    }

}
