package com.increff.store.controller;

import com.increff.store.dto.OrderDto;
import com.increff.store.model.DateFilterForm;
import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Adds order to order table")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public String add(@RequestBody OrderForm form) throws ApiException
    {
        String message;
        try {
            dto.insert(form);
            message = "Order Created Successfully";
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }

        return message;
    }

    @ApiOperation(value = "Select all order")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> get_all() throws  ApiException
    {
        return dto.get_all();
    }

    @ApiOperation(value = "Select all order between given date")
    @RequestMapping(path = "/api/order/date-filter", method = RequestMethod.POST)
    public List<OrderData> get_date_filter(@RequestBody DateFilterForm form) throws ApiException
    {
        return dto.get_date_filter(form);
    }

    @ApiOperation(value = "Select order by id")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get_id(@PathVariable int id) throws  ApiException
    {
        return dto.get_id(id);
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{id}", method = RequestMethod.PUT)
    public String mark_order_placed(@PathVariable int id) throws ApiException
    {
        String message;
        try{
            dto.place_order(id);
            message = "Order Placed Successfully";
        }
        catch (Exception e)
        {
            message = e.getMessage();
        }
        return message;
    }

}
