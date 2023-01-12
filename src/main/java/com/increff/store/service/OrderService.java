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
    public Integer add(OrderPojo p) throws ApiException
    {
        return dao.insert(p);
    }

    @Transactional
    public List<OrderPojo> select_all() throws ApiException
    {
        return dao.selectAll();
    }

    @Transactional
    public List<OrderPojo> select_date_filter(String start, String end) throws ApiException
    {
        return dao.selectByDateFilter(start, end);
    }

    @Transactional
    public OrderPojo get_id(int id) throws ApiException
    {
        OrderPojo orderPojo = dao.selectById(id);
        if(orderPojo == null)
            throw new ApiException("No order found with given order id");
        return orderPojo;
    }

    @Transactional
    public void update(int id, OrderPojo newOrderPojo) throws ApiException
    {
        OrderPojo p = dao.selectById(id);
        p.setStatus(newOrderPojo.getStatus());
        p.setPlaceDateTime(newOrderPojo.getPlaceDateTime());
        p.setCreatedDateTime(newOrderPojo.getCreatedDateTime());
        p.setCustomerName(newOrderPojo.getCustomerName());
    }

    public OrderPojo get_order_orderCode(String orderCode) {
        return dao.selectByOrderCode(orderCode);
    }
}
