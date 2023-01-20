package com.increff.store.controller;

import com.increff.store.dto.InventoryDto;
import com.increff.store.model.InventoryData;
import com.increff.store.model.InventoryForm;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/api/admin/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        dto.addInventory(form);
    }

    @ApiOperation(value = "Removes an inventory")
    @RequestMapping(path = "/api/admin/inventory", method = RequestMethod.PUT)
    public void remove(@RequestBody InventoryForm form) throws ApiException {
        dto.reduceInventory(form);
    }

    @ApiOperation(value = "Select all inventory")
    @RequestMapping(path = "api/inventory", method = RequestMethod.GET)
    public List<InventoryData> get_all() {
        return dto.getAllInventory();
    }

}
