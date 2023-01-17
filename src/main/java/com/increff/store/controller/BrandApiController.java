package com.increff.store.controller;

import com.increff.store.dto.BrandDto;
import com.increff.store.invoice.InvoiceGenerator;
import com.increff.store.model.BrandData;
import com.increff.store.model.BrandForm;
import com.increff.store.model.InvoiceForm;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandDto dto;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        dto.addBrand(form);
    }

    @ApiOperation(value = "Select all brands")
    @RequestMapping(path = "api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return dto.getAllBrands();
    }

    @ApiOperation(value = "Update brand")
    @RequestMapping(path = "api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
        dto.updateBrand(id, form);
    }

}
