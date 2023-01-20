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

    @ApiOperation(value = "Get a product")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.GET)
    public ProductData get_id(@PathVariable Integer id) throws ApiException {
        return dto.getProductById(id);
    }

    @ApiOperation(value = "Select all products")
    @RequestMapping(path = "api/product", method = RequestMethod.GET)
    public List<ProductData> get_all() throws ApiException {
        return dto.getAllProducts();
    }

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/admin/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        dto.addProduct(form);
    }

    @ApiOperation(value = "Update product")
    @RequestMapping(path = "api/admin/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm form) throws ApiException {
        dto.updateProduct(id, form);
    }

}
