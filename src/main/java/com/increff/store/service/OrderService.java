package com.increff.store.service;

import com.increff.store.dao.OrderDao;
import com.increff.store.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao dao;

    @Transactional
    public void add(OrderPojo p) throws ApiException
    {
        dao.insert(p);
    }

    @Transactional
    public List<OrderPojo> select_all() throws ApiException
    {
        return dao.select_all();
    }

    @Transactional
    public List<OrderPojo> select_date_filter(String start, String end) throws ApiException
    {
        return dao.select_date_filter(start, end);
    }

    @Transactional
    public OrderPojo get_id(int id) throws ApiException
    {
        return dao.get_id(id);
    }

    @Transactional
    public void update(int id, OrderPojo newOrderPojo) throws ApiException
    {
        OrderPojo p = dao.get_id(id);
        p.setStatus(newOrderPojo.getStatus());
        p.setPlaceDateTime(newOrderPojo.getPlaceDateTime());
        p.setCreatedDateTime(newOrderPojo.getCreatedDateTime());
        p.setCustomerName(newOrderPojo.getCustomerName());
    }

    public OrderPojo get_order_orderCode(String orderCode) {
        try{
            OrderPojo p = dao.get_order_orderCode(orderCode);
            return p;
        }
        catch (Exception e) {
            return null;
        }
    }
}
