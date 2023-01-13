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
    public void addOrder(OrderPojo p) throws ApiException {
        try {
            dao.insert(p);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public List<OrderPojo> selectAllOrders() throws ApiException {
        return dao.selectAll();
    }

    @Transactional
    public List<OrderPojo> selectOrderByDateFilter(String start, String endDate) throws ApiException {
        try {
            return dao.selectByDateFilter(start, endDate);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public OrderPojo getOrderById(int id) throws ApiException {
        OrderPojo orderPojo = dao.selectById(id);
        if (orderPojo == null)
            throw new ApiException("No order found with given order id");
        return orderPojo;
    }

    @Transactional
    public void updateOrder(int id, OrderPojo newOrderPojo) throws ApiException {
        OrderPojo p = dao.selectById(id);
        p.setStatus(newOrderPojo.getStatus());
        p.setPlaceDateTime(newOrderPojo.getPlaceDateTime());
        p.setCreatedDateTime(newOrderPojo.getCreatedDateTime());
        p.setCustomerName(newOrderPojo.getCustomerName());
    }

    public OrderPojo getOrderByOrderCode(String orderCode) {
        return dao.selectByOrderCode(orderCode);
    }
}
