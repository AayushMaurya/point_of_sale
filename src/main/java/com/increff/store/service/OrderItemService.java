package com.increff.store.service;

import com.increff.store.dao.OrderItemDao;
import com.increff.store.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderItemDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void addOrderItem(OrderItemPojo pojo) throws ApiException {
        dao.insert(pojo);
    }

    @Transactional
    public List<OrderItemPojo> getOrder(Integer id) {
        return dao.selectById(id);
    }

    @Transactional
    public List<OrderItemPojo> getAllOrderItem() {
        return dao.selectAll();
    }

    @Transactional
    public OrderItemPojo getOrderItemById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = dao.selectByItemId(id);
        if (orderItemPojo == null)
            throw new ApiException("Cannot find an item with given order item id");
        return orderItemPojo;
    }

    public OrderItemPojo selectByItemId(Integer id) throws ApiException
    {
        OrderItemPojo orderItemPojo = dao.selectByItemId(id);
        if(orderItemPojo == null)
            throw new ApiException("No order item found with given id");
        return orderItemPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void deleteOrderItemById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = selectByItemId(id);
        dao.deleteById(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateOrderItem(Integer id, OrderItemPojo newPojo) throws ApiException {
        OrderItemPojo oldPojo = getOrderItemById(id);

        oldPojo.setQuantity(newPojo.getQuantity());
        oldPojo.setOrderId(newPojo.getOrderId());
        oldPojo.setSellingPrice(newPojo.getSellingPrice());
        oldPojo.setProductId(newPojo.getProductId());
    }

    public OrderItemPojo getProductIdOrderId(Integer productId, Integer orderId) throws ApiException {
        return dao.selectByProductIdOrderId(productId, orderId);
    }
}
