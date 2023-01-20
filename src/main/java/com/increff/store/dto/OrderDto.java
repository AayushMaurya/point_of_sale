package com.increff.store.dto;

import com.increff.store.model.DateFilterForm;
import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.store.dto.DtoUtils.*;
import static com.increff.store.util.CreateRandomSequence.createRandomOrderCode;
import static com.increff.store.util.GetCurrentDataTime.getCurrentDateTime;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    public Integer createOrder() throws ApiException
    {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName("");
        orderPojo.setCreatedDateTime(getCurrentDateTime());
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime("");

//        creating random order code
        String orderCode = createRandomOrderCode();

//        OrderPojo x = service.getOrderByOrderCode(orderCode);
//        while(x!=null)
//        {
//            orderCode = createRandomOrderCode();
//            x = service.getOrderByOrderCode(orderCode);
//        }

        orderPojo.setOrderCode(orderCode);
        return service.addOrder(orderPojo);
    }

    public List<OrderData> getAllOrders() throws ApiException
    {
        List<OrderData> list1 = new ArrayList<OrderData>();
        List<OrderPojo> list2 = service.selectAllOrders();

        for(OrderPojo p: list2)
            list1.add(convertOrderPojoToOrderData(p));

        return list1;
    }

    public List<OrderData> getOrderByDateFilter(DateFilterForm form) throws ApiException
    {
        List<OrderData> list1 = new ArrayList<OrderData>();

        String startDate = form.getStart().replace('-', '/');
        String endDate = form.getEnd().replace('-', '/');
        List<OrderPojo> list2 = service.selectOrderByDateFilter(startDate, endDate);

        for(OrderPojo p: list2)
            list1.add(convertOrderPojoToOrderData(p));

        return list1;
    }

    public OrderData getOrderById(Integer id) throws ApiException
    {
        OrderPojo p = service.getOrderById(id);
        return convertOrderPojoToOrderData(p);
    }

//    public OrderData getOrderByOrderCode(String orderCode) throws ApiException {
//        OrderPojo p = service.getOrderByOrderCode(orderCode);
//        return convertOrderPojoToOrderData(p);
//    }

    public void placeOrder(Integer id, OrderForm form) throws ApiException
    {
        checkOrderForm(form);
        OrderPojo orderPojo = service.getOrderById(id);
        orderPojo.setStatus("Placed");
        orderPojo.setCustomerName(form.getCustomerName());
        orderPojo.setPlaceDateTime(getCurrentDateTime());
        normalize(orderPojo);
        service.updateOrder(id, orderPojo);
    }
}
