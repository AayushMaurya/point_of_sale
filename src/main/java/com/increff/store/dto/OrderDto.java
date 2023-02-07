package com.increff.store.dto;

import com.increff.store.client.InvoiceClient;
import com.increff.store.flow.OrderItemFlow;
import com.increff.store.model.data.OrderData;
import com.increff.store.model.form.DateFilterForm;
import com.increff.store.model.form.OrderForm;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.OrderItemService;
import com.increff.store.api.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
        OrderPojo orderPojo = initializeOrderPojo();
        service.addOrder(orderPojo);
        return orderPojo.getOrderCode();
    }

    public List<OrderData> getAllOrders() throws ApiException {
        List<OrderData> list1 = new ArrayList<OrderData>();
        List<OrderPojo> list2 = service.selectAllOrders();
        for (OrderPojo p : list2)
            list1.add(convertOrderPojoToOrderData(p));

        return list1;
    }

    public List<OrderData> getOrderByDateFilter(DateFilterForm form) throws ApiException {

        LocalDateTime startDate = stringDateToLocalDate(form.getStart()).atStartOfDay();
        LocalDateTime endDate = stringDateToLocalDate(form.getEnd()).atTime(23, 59, 59);

        List<OrderPojo> orderPojoList = service.selectOrderByDateFilter(startDate, endDate);

        List<OrderData> orderDataList = new ArrayList<OrderData>();

        for (OrderPojo p : orderPojoList)
            orderDataList.add(convertOrderPojoToOrderData(p));

        return orderDataList;
    }

    public OrderData getOrderById(Integer orderId) throws ApiException {
        OrderPojo pojo = service.getOrderById(orderId);
        return convertOrderPojoToOrderData(pojo);
    }

    public OrderData getOrderByOrderCode(String orderCode) throws ApiException {
        OrderPojo pojo = service.getOrderByOrderCode(orderCode);
        return convertOrderPojoToOrderData(pojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void placeOrder(Integer orderId, OrderForm form) throws ApiException {
        checkOrderForm(form);

        OrderPojo orderPojo = service.getOrderById(orderId);
        checkOrderPlaceable(orderId, orderPojo.getStatus());

        orderPojo.setStatus("Placed");
        orderPojo.setCustomerName(form.getCustomerName());
        orderPojo.setPlaceDateTime(getCurrentDateTime());
        normalizeOrderPojo(orderPojo);

        service.updateOrder(orderId, orderPojo);
        invoiceClient.downloadInvoice(orderId);
    }

    //    method to delete all unplaced orders from one day before
    public void deleteUnplacedOrders() throws ApiException {
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

    private void checkOrderPlaceable(Integer orderId, String status) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getOrder(orderId);
        if (list.size() == 0)
            throw new ApiException("Add at least one item");
        if (Objects.equals(status, "Placed"))
            throw new ApiException("Order already placed");
    }
}
