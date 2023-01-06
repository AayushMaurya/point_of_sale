package com.increff.store.controller;

import com.increff.store.dto.InventoryDto;
import com.increff.store.model.InventoryData;
import com.increff.store.model.InventoryForm;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public String add(@RequestBody InventoryForm form) throws ApiException
    {
        String message;
        try {
            dto.add(form);
            message = "Inventory successfully updated";
        }
        catch(Exception e)
        {
            message = e.getMessage();
        }

        return message;
    }

    @ApiOperation(value = "Removes an inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.PUT)
    public void remove(@RequestBody InventoryForm form) throws ApiException
    {
        dto.remove(form);
    }

//    @ApiOperation(value = "Select inventory by Id")
//    @RequestMapping(path = "api/inventory/{id}", method = RequestMethod.GET)
//    public InventoryData get_id(@PathVariable int id)
//    {
//        InventoryPojo p = service.get(id);
//        return convert(p);
//    }

    @ApiOperation(value = "Select all inventory")
    @RequestMapping(path = "api/inventory", method = RequestMethod.GET)
    public List<InventoryData> get_all()
    {
        return dto.get_all();
    }

//    @ApiOperation(value = "Delete inventory by id")
//    @RequestMapping(path = "api/inventory/{id}", method = RequestMethod.DELETE)
//    public void delete_id(@PathVariable int id)
//    {
//        service.delete_id(id);
//    }

}
