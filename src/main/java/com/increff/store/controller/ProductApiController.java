package com.increff.store.controller;

import com.increff.store.dto.ProductDto;
import com.increff.store.model.ProductData;
import com.increff.store.model.ProductForm;
import com.increff.store.service.ApiException;
import com.increff.store.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProductApiController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.GET)
    public ProductData get_id(@PathVariable int id) {
        return dto.get(id);
    }

    @ApiOperation(value = "Select all products")
    @RequestMapping(path = "api/product", method = RequestMethod.GET)
    public List<ProductData> get_all() {
        return dto.get_all();
    }

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public String add(@RequestBody ProductForm form) throws ApiException {
        String message;
        try {
            dto.add(form);
            message = "Successfully added new product";
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }

        return message;
    }

    @ApiOperation(value = "Delete product by id")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.DELETE)
    public void delete_id(@PathVariable int id) {
        service.delete_id(id);
    }

    @ApiOperation(value = "Update product")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
        dto.update(id, form);
    }

}
