package com.increff.store.controller;

import com.increff.store.dto.OrderItemDto;
import com.increff.store.flow.OrderItemFlow;
import com.increff.store.model.OrderItemData;
import com.increff.store.model.OrderItemForm;
import com.increff.store.model.UpdateOrderItemForm;
import com.increff.store.api.ApiException;
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
    private OrderItemFlow flow;

    @ApiOperation(value = "Adds an orderItem")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form) throws ApiException {
        dto.addOrderItem(form);
    }

    @ApiOperation(value = "Gets an order by orderId")
    @RequestMapping(path = "/api/order-item/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getOrderByOrderId(@PathVariable Integer id) throws ApiException {
        return dto.getOrderItemByOrderId(id);
    }

    @ApiOperation(value = "Get all order items")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.GET)
    public List<OrderItemData> getAll() throws ApiException {
        return dto.getAllOrderItem();
    }

    @ApiOperation(value = "Delete an item by item id")
    @RequestMapping(path = "api/order-item/{id}", method = RequestMethod.DELETE)
    public void deleteItemId(@PathVariable Integer id) throws ApiException {
        flow.deleteOrderItemById(id);
    }

    @ApiOperation(value = "Update Order Item")
    @RequestMapping(path = "/api/order-item", method = RequestMethod.PUT)
    public void update(@RequestBody UpdateOrderItemForm form) throws ApiException {
        dto.updateOrderItem(form);
    }
}
