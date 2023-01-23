package com.increff.store.service;

import com.increff.store.dao.OrderItemDao;
import com.increff.store.pojo.InventoryPojo;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void addOrderItem(OrderItemPojo p) throws ApiException {
        Integer orderId = p.getOrderId();
        OrderPojo orderPojo = orderService.getOrderById(orderId);
        if (orderPojo == null)
            throw new ApiException("No order found with given order id");

        dao.insert(p);
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(p.getQuantity());
        inventoryPojo.setId(p.getProductId());
        inventoryService.reduceInventory(inventoryPojo);
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

    @Transactional(rollbackOn = ApiException.class)
    public void deleteOrderItemById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = dao.selectByItemId(id);
        if (orderItemPojo == null)
            throw new ApiException("Cannot find an order item with given id");

//        updating the inventory
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(orderItemPojo.getProductId());
        inventoryPojo.setQuantity(orderItemPojo.getQuantity());
        inventoryService.addInventory(inventoryPojo);

        dao.deleteById(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateOrderItem(Integer id, OrderItemPojo newPojo) throws ApiException {
        OrderItemPojo oldPojo = getOrderItemById(id);
        Integer oldQuantity = oldPojo.getQuantity();
        Integer newQuantity = newPojo.getQuantity();
        InventoryPojo inventoryPojo = new InventoryPojo();

//        this has to be in flow

//        adding old quantity to old inventory
        inventoryPojo.setId(oldPojo.getProductId());
        inventoryPojo.setQuantity(oldQuantity);
        inventoryService.addInventory(inventoryPojo);
//        removing new quantity to new inventory
        inventoryPojo.setId(newPojo.getProductId());
        inventoryPojo.setQuantity(newQuantity);
        inventoryService.reduceInventory(inventoryPojo);

        oldPojo.setQuantity(newPojo.getQuantity());
        oldPojo.setOrderId(newPojo.getOrderId());
        oldPojo.setSellingPrice(newPojo.getSellingPrice());
        oldPojo.setProductId(newPojo.getProductId());
    }

    public OrderItemPojo getProductIdOrderId(Integer productId, Integer orderId) throws ApiException {
        return dao.selectByProductIdOrderId(productId, orderId);
    }
}
