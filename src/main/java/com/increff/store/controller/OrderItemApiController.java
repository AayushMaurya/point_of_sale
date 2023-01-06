package com.increff.store.controller;

import com.increff.store.dto.OrderItemDto;
import com.increff.store.model.OrderItemData;
import com.increff.store.model.OrderItemForm;
import com.increff.store.model.UpdateOrderItemForm;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemDto dto;

    @Autowired
    private OrderItemService service;

    @ApiOperation(value = "Adds an orderItem")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.POST)
    public String add(@RequestBody OrderItemForm form) throws ApiException
    {
        String message;
        try {
            dto.add(form);
            message = "Successfully added order item";
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    @ApiOperation(value = "Gets an order by orderId")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.GET)
    public List<OrderItemData> get_orderId(@PathVariable int id)
    {
        return dto.get_orderId(id);
    }

    @ApiOperation(value = "Gets an order by orderCode")
    @RequestMapping(path = "/api/order-item/order-code/{id}", method = RequestMethod.GET)
    public List<OrderItemData> get_orderId(@PathVariable String id)
    {
        return dto.get_orderCode(id);
    }

    @ApiOperation(value = "Gets an order by orderId")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.GET)
    public List<OrderItemData> get_all()
    {
        return dto.get_all();
    }

    @ApiOperation(value = "Delete an item by item id")
    @RequestMapping(path = "api/order-item/{id}", method = RequestMethod.DELETE)
    public String delete_ItemId(@PathVariable int id) throws ApiException
    {
        String message;
        try {
            service.delete_ItemId(id);
            message = "Successfully delete the order item: " + id;
        }
        catch (Exception e) {
            message = e.getMessage();
        }

        return message;
    }

    @ApiOperation(value = "Update Order Item")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.PUT)
    public String update(@RequestBody UpdateOrderItemForm form)
    {
        String message;
        try{
            dto.update(form);
            message = "successfully updated";
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }
}
