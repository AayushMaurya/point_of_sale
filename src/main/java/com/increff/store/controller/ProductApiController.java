package com.increff.store.controller;

import com.increff.store.dto.ProductDto;
import com.increff.store.model.data.ProductData;
import com.increff.store.model.form.ProductForm;
import com.increff.store.model.form.UpdateProductForm;
import com.increff.store.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ProductApiController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Get a product")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.GET)
    public ProductData getProductById(@PathVariable Integer id) throws ApiException {
        return dto.getProductById(id);
    }

    @ApiOperation(value = "Select all products")
    @RequestMapping(path = "api/product", method = RequestMethod.GET)
    public List<ProductData> getAllProducts() throws ApiException {
        return dto.getAllProducts();
    }

    @ApiOperation(value = "Adds a product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        dto.addProduct(form);
    }

    @ApiOperation(value = "Update product")
    @RequestMapping(path = "api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody UpdateProductForm form) throws ApiException {
        dto.updateProduct(id, form);
    }

}
