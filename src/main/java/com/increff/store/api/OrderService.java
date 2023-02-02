package com.increff.store.api;

import com.increff.store.dao.OrderDao;
import com.increff.store.pojo.OrderPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {
    @Autowired
    private OrderDao dao;

    private static Logger logger = Logger.getLogger(OrderService.class);

    public Integer addOrder(OrderPojo p) throws ApiException {
        return dao.insert(p);
    }

    public List<OrderPojo> selectAllOrders() throws ApiException {
        return dao.selectAll();
    }

    public List<OrderPojo> selectOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) throws ApiException {
        return dao.selectByDateFilter(startDate, endDate);
    }

    public OrderPojo getOrderById(Integer id) throws ApiException {
        OrderPojo orderPojo = dao.selectById(id);
        if (orderPojo == null)
            throw new ApiException("No order found with given order id");
        return orderPojo;
    }

    public void updateOrder(Integer id, OrderPojo newOrderPojo) throws ApiException {
        OrderPojo pojo = dao.selectById(id);
        if (pojo == null) {
            logger.error("Order pojo to be update is null");
            throw new ApiException("Cannot place order");
        }
        pojo.setStatus(newOrderPojo.getStatus());
        pojo.setPlaceDateTime(newOrderPojo.getPlaceDateTime());
        pojo.setCustomerName(newOrderPojo.getCustomerName());
    }

    public OrderPojo getOrderByOrderCode(String orderCode) throws ApiException {
        return dao.selectByOrderCode(orderCode);
    }

    public List<OrderPojo> getAllUnplacedOrders() {
        return dao.selectAllUnplacedOrders();
    }

    public void deleteOrder(Integer id) {
        dao.deleteOrder(id);
    }
}
