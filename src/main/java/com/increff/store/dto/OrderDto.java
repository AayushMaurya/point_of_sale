package com.increff.store.dto;

import com.increff.store.client.InvoiceClient;
import com.increff.store.flow.OrderItemFlow;
import com.increff.store.model.*;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import com.increff.store.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.increff.store.dto.DtoUtils.*;
import static com.increff.store.util.GetCurrentDataTime.getCurrentDateTime;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;
    @Autowired
    InvoiceClient invoiceClient;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemFlow orderItemFlow;

    private static Logger logger = Logger.getLogger(ReportDto.class);

    public String createOrder() throws ApiException {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName("");
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime(null);

//        creating random order code
        String orderCode = UUID.randomUUID().toString();

        orderPojo.setOrderCode(orderCode);

        service.addOrder(orderPojo);

        return orderCode;
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
        normalizeOrderPojo(orderPojo);
        service.updateOrder(id, orderPojo);
        try {
            invoiceClient.downloadInvoice(id);
        } catch (Exception e) {
            throw new ApiException("Cannot create Invoice for given order");
        }
    }

    //    method to delete all unplaced orders from one day before
    @Scheduled(cron = "${cron.expression}")
    public void deleteUnplacedOrders() throws ApiException {
        logger.info("Deleting all unplaced orders");
        List<OrderPojo> orderPojoList = service.getAllUnplacedOrders();
        for (OrderPojo p : orderPojoList) {
            if (getCurrentDateTime().minusDays(1).isAfter(p.getCreatedAt()))
                deleteOrder(p.getId());
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public void deleteOrder(Integer id) throws ApiException {
        logger.info("Deleting order: " + id);
        List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(id);
        for (OrderItemPojo p : orderItemPojoList)
            orderItemFlow.deleteOrderItemById(p.getId());
        service.deleteOrder(id);
    }
}
