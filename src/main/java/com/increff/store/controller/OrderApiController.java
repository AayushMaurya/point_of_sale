package com.increff.store.controller;

import com.increff.store.dto.OrderDto;
import com.increff.store.invoice.InvoiceGenerator;
import com.increff.store.model.DateFilterForm;
import com.increff.store.model.InvoiceForm;
import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.pojo.OrderPojo;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Integer createOrder() throws ApiException {
        return dto.createOrder();
    }

    @ApiOperation(value = "Select all order")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderData> getAllOrders() throws ApiException {
        return dto.getAllOrders();
    }

    @ApiOperation(value = "Select all order between given date")
    @RequestMapping(path = "/api/order/date-filter", method = RequestMethod.POST)
    public List<OrderData> getDateFilter(@RequestBody DateFilterForm form) throws ApiException {
        return dto.getOrderByDateFilter(form);
    }

    @ApiOperation(value = "Select order by id")
    @RequestMapping(path = "/api/order/{orderId}", method = RequestMethod.GET)
    public OrderData getOrderById(@PathVariable Integer orderId) throws ApiException {
        return dto.getOrderById(orderId);
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{orderId}", method = RequestMethod.PUT)
    public void markOrderPlaced(@PathVariable Integer orderId, @RequestBody OrderForm form) throws Exception {
        dto.placeOrder(orderId, form);

//        generating invoice
        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderId);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/fop/api/invoice";
//
        byte[] contents = restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody();

//        saving pdf;
        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderId + "_invoice.pdf");
//
        Files.write(pdfPath, contents);
    }

    @ApiOperation(value = "Download Invoice")
    @RequestMapping(path = "/api/invoice/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF(@PathVariable Integer orderId) throws Exception {

        OrderData orderData = dto.getOrderById(orderId);

        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderData.getId() + "_invoice.pdf");

        byte[] contents = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = orderData.getCustomerName() + ".pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }


}
