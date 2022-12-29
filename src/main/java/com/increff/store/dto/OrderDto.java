package com.increff.store.dto;

import com.increff.store.model.DateFilterForm;
import com.increff.store.model.OrderData;
import com.increff.store.model.OrderForm;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderService;
import com.increff.store.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.store.util.GetCurrentDataTime.get_current_dat_time;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    public void insert(OrderForm form) throws ApiException
    {
        OrderPojo orderPojo = convert(form);
        orderPojo.setCreatedDateTime(get_current_dat_time());
        orderPojo.setStatus("pending");
        orderPojo.setPlaceDateTime("");
        normalize(orderPojo);
        service.add(orderPojo);
    }

    public List<OrderData> get_all() throws ApiException
    {
        List<OrderData> list1 = new ArrayList<OrderData>();
        List<OrderPojo> list2 = service.select_all();

        for(OrderPojo p: list2)
            list1.add(convert(p));

        return list1;
    }

    public List<OrderData> get_date_filter(DateFilterForm form) throws ApiException
    {
        List<OrderData> list1 = new ArrayList<OrderData>();
        List<OrderPojo> list2 = service.select_date_filter(form.getStart(), form.getEnd());

        for(OrderPojo p: list2)
            list1.add(convert(p));

        return list1;
    }

    public OrderData get_id(int id) throws ApiException
    {
        OrderPojo p = service.get_id(id);
        return convert(p);
    }

    public void place_order(int id) throws ApiException
    {
        OrderPojo orderPojo = service.get_id(id);
        orderPojo.setStatus("Placed");
        orderPojo.setPlaceDateTime(get_current_dat_time());
        service.update(id, orderPojo);
    }

    private OrderPojo convert(OrderForm form)
    {
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setCustomerName(form.getCustomerName());
        return orderPojo;
    }

    private OrderData convert(OrderPojo p)
    {
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        orderData.setCreatedDataTime(p.getCreatedDateTime());
        orderData.setPlacedDataTime(p.getPlaceDateTime());
        orderData.setCustomerName(p.getCustomerName());
        orderData.setStatus(p.getStatus());

        return orderData;
    }

    private void normalize(OrderPojo p)
    {
        p.setCustomerName(StringUtil.toLowerCase(p.getCustomerName()));
    }

}
