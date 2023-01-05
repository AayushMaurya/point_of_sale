package com.increff.store.controller;

import com.increff.store.dto.BrandDto;
import com.increff.store.model.BrandData;
import com.increff.store.model.BrandForm;
import com.increff.store.service.ApiException;
import com.increff.store.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandService service;

    @Autowired
    private BrandDto dto;

    @ApiOperation(value = "Adds a brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public String add(@RequestBody BrandForm form) throws ApiException
    {
        String response;
        try {
            dto.add(form);
            response = "successfully added";
        }
        catch(Exception e)
        {
            response = e.getMessage();
        }
        return response;
    }

//    @ApiOperation(value = "Select brand by Id")
//    @RequestMapping(path = "api/brand/{id}", method = RequestMethod.GET)
//    public BrandData get_id(@PathVariable int id) throws ApiException
//    {
//        return dto.get(id);
//    }

    @ApiOperation(value = "Select all brands")
    @RequestMapping(path = "api/brand", method = RequestMethod.GET)
    public List<BrandData> get_all()
    {
        return dto.get_all();
    }

//    @ApiOperation(value = "Delete brand by id")
//    @RequestMapping(path = "api/brand/{id}", method = RequestMethod.DELETE)
//    public void delete_id(@PathVariable int id)
//    {
//        service.delete_id(id);
//    }

    @ApiOperation(value = "Update brand")
    @RequestMapping(path = "api/brand/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException
    {
        String message;
        try {
            dto.update(id, form);
            message = "successfully updated";
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }

        return message;
    }

}
