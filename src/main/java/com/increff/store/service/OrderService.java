package com.increff.store.service;

import com.google.protobuf.Api;
import com.increff.store.dao.OrderDao;
import com.increff.store.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao dao;

    @Transactional
    public String addOrder(OrderPojo p) throws ApiException {
        return dao.insert(p);
    }

    @Transactional
    public List<OrderPojo> selectAllOrders() throws ApiException {
        return dao.selectAll();
    }

    @Transactional
    public List<OrderPojo> selectOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) throws ApiException {
        try {
            return dao.selectByDateFilter(startDate, endDate);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public OrderPojo getOrderById(Integer id) throws ApiException {
        OrderPojo orderPojo = dao.selectById(id);
        if (orderPojo == null)
            throw new ApiException("No order found with given order id");
        return orderPojo;
    }

    @Transactional
    public void updateOrder(Integer id, OrderPojo newOrderPojo) throws ApiException {
        OrderPojo p = dao.selectById(id);
        p.setStatus(newOrderPojo.getStatus());
        p.setPlaceDateTime(newOrderPojo.getPlaceDateTime());
        p.setCustomerName(newOrderPojo.getCustomerName());
    }

    public OrderPojo getOrderByOrderCode(String orderCode) throws ApiException {
        return dao.selectByOrderCode(orderCode);
    }
}
