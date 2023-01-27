package com.increff.store.dto;

import com.increff.store.model.*;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import com.increff.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.increff.store.invoice.InvoiceGenerator;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.store.dto.DtoUtils.*;
import static com.increff.store.util.CreateRandomSequence.createRandomOrderCode;
import static com.increff.store.util.GetCurrentDataTime.getCurrentDateTime;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;

    public String createOrder() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName("");
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime(null);

//        creating random order code
        String orderCode = createRandomOrderCode();

        OrderPojo x = service.getOrderByOrderCode(orderCode);
        while (x != null) {
            orderCode = createRandomOrderCode();
            x = service.getOrderByOrderCode(orderCode);
        }

        orderPojo.setOrderCode(orderCode);
        return service.addOrder(orderPojo);
    }

    public List<OrderData> getAllOrders() throws ApiException {
        List<OrderData> list1 = new ArrayList<OrderData>();
        List<OrderPojo> list2 = service.selectAllOrders();
        for (OrderPojo p : list2)
            list1.add(convertOrderPojoToOrderData(p));

        return list1;
    }

    public List<OrderData> getOrderByDateFilter(DateFilterForm form) throws ApiException {
        List<OrderData> list1 = new ArrayList<OrderData>();

        LocalDateTime startDate;
        LocalDateTime endDate;

//      converting to proper date time format
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            startDate = LocalDate.parse(form.getStart(), formatter).atStartOfDay();
            endDate = LocalDate.parse(form.getEnd(), formatter).atTime(23, 59, 59);
        } catch (Exception e) {
            throw new ApiException("Please input valid start and end date");
        }

        List<OrderPojo> list2 = service.selectOrderByDateFilter(startDate, endDate);

        for (OrderPojo p : list2)
            list1.add(convertOrderPojoToOrderData(p));

        return list1;
    }

    public OrderData getOrderById(Integer id) throws ApiException {
        OrderPojo p = service.getOrderById(id);
        return convertOrderPojoToOrderData(p);
    }

    public OrderData getOrderByOrderCode(String orderCode) throws ApiException {
        OrderPojo p = service.getOrderByOrderCode(orderCode);
        return convertOrderPojoToOrderData(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void placeOrder(Integer id, OrderForm form) throws ApiException {
        checkOrderForm(form);
        OrderPojo orderPojo = service.getOrderById(id);
        List<OrderItemPojo> list = orderItemService.getOrder(id);
        if (list.size() == 0)
            throw new ApiException("Add at least one item");
        if (Objects.equals(orderPojo.getStatus(), "Placed"))
            throw new ApiException("Order already placed");
        orderPojo.setStatus("Placed");
        orderPojo.setCustomerName(form.getCustomerName());
        orderPojo.setPlaceDateTime(getCurrentDateTime());
        normalize(orderPojo);
        service.updateOrder(id, orderPojo);
        try {
            downloadInvoice(id);
        } catch (Exception e) {
            throw new ApiException("Cannot create Invoice for given order");
        }
    }

    private void downloadInvoice(Integer orderId) throws Exception {

//        generating invoice form
        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderId);

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/fop/api/invoice";

        byte[] contents = restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody();

//        saving pdf;
        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderId + "_invoice.pdf");

        Files.write(pdfPath, contents);
    }
}
