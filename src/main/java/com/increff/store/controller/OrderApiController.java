package com.increff.store.controller;

import com.increff.store.dto.OrderDto;
import com.increff.store.invoice.InvoiceGenerator;
import com.increff.store.model.DateFilterForm;
import com.increff.store.model.InvoiceForm;
import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto dto;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @ApiOperation(value = "Adds order to order table")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public String add(@RequestBody OrderForm form) throws ApiException {
        return dto.createOrder(form);
    }

    @ApiOperation(value = "Select all order")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> get_all() throws ApiException {
        return dto.getAllOrders();
    }

    @ApiOperation(value = "Select all order between given date")
    @RequestMapping(path = "/api/order/date-filter", method = RequestMethod.POST)
    public List<OrderData> get_date_filter(@RequestBody DateFilterForm form) throws ApiException {
        return dto.getOrderByDateFilter(form);
    }

    @ApiOperation(value = "Select order by id")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderData get_id(@PathVariable int id) throws ApiException {
        return dto.getOrderById(id);
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{id}", method = RequestMethod.PUT)
    public void mark_order_placed(@PathVariable int id) throws ApiException {
        dto.placeOrder(id);
    }

    @ApiOperation(value = "Download Invoice")
    @RequestMapping(path = "/api/invoice/{orderCode}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF(@PathVariable String orderCode) throws Exception{

        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderCode);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/fop/api/invoice";

        byte[] contents = restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }


}
