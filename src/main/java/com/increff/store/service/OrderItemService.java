package com.increff.store.service;

import com.increff.store.dao.OrderItemDao;
import com.increff.store.pojo.OrderItemPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    private static Logger logger = Logger.getLogger(OrderItemService.class);

    public Integer addOrderItem(OrderItemPojo pojo) throws ApiException {
        return dao.insert(pojo);
    }

    public List<OrderItemPojo> getOrder(Integer id) {
        return dao.selectById(id);
    }

    public List<OrderItemPojo> getAllOrderItem() {
        return dao.selectAll();
    }

    public OrderItemPojo getOrderItemById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = dao.selectByItemId(id);
        if (orderItemPojo == null)
            throw new ApiException("Cannot find an item with given order item id");
        return orderItemPojo;
    }

    public void deleteOrderItemById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = getOrderItemById(id);
        dao.deleteById(id);
    }

    public void updateOrderItem(Integer id, OrderItemPojo newPojo) throws ApiException {
        OrderItemPojo oldPojo = getOrderItemById(id);
        oldPojo.setQuantity(newPojo.getQuantity());
        oldPojo.setSellingPrice(newPojo.getSellingPrice());
    }

    public OrderItemPojo getProductIdOrderId(Integer productId, Integer orderId) throws ApiException {
        return dao.selectByProductIdOrderId(productId, orderId);
    }
}
