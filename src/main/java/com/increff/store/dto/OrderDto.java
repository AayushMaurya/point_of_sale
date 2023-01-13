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
import static com.increff.store.util.GetCurrentDataTime.get_current_dat_time;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    public String createOrder(OrderForm form) throws ApiException
    {
        OrderPojo orderPojo = convertOrderFormToOrderPojo(form);
        orderPojo.setCreatedDateTime(get_current_dat_time());
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime("");
        normalize(orderPojo);

//        creating random order code
        String orderCode = createRandomOrderCode();

        OrderPojo x = service.getOrderByOrderCode(orderCode);
        while(x!=null)
        {
            orderCode = createRandomOrderCode();
            x = service.getOrderByOrderCode(orderCode);
        }

        orderPojo.setOrderCode(orderCode);
        service.addOrder(orderPojo);

        return orderCode;
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

    public OrderData getOrderById(int id) throws ApiException
    {
        OrderPojo p = service.getOrderById(id);
        return convertOrderPojoToOrderData(p);
    }

    public OrderData getOrderByOrderCode(String id) throws ApiException {
        OrderPojo p = service.getOrderByOrderCode(id);
        return convertOrderPojoToOrderData(p);
    }

    public void placeOrder(int id) throws ApiException
    {
        OrderPojo orderPojo = service.getOrderById(id);
        orderPojo.setStatus("Placed");
        orderPojo.setPlaceDateTime(get_current_dat_time());
        service.updateOrder(id, orderPojo);
    }
}
