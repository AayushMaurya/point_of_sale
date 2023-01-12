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
        int orderId = p.getOrderId();
        OrderPojo orderPojo = orderService.get_id(orderId);
        if (orderPojo == null)
            throw new ApiException("No order found with given order id");

        dao.insert(p);
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(p.getQuantity());
        inventoryPojo.setId(p.getProductId());
        inventoryService.reduceInventory(inventoryPojo);
    }

    @Transactional
    public List<OrderItemPojo> get_order(int id) {
        return dao.selectById(id);
    }

    @Transactional
    public List<OrderItemPojo> get_all() {
        return dao.selectAll();
    }

    @Transactional
    public OrderItemPojo get_id(int id) throws ApiException {
        OrderItemPojo orderItemPojo = dao.selectByItemId(id);
        if (orderItemPojo == null)
            throw new ApiException("Cannot find an order with given order item id");
        return orderItemPojo;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete_ItemId(int id) throws ApiException {
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
    public void update(int id, OrderItemPojo newPojo) throws ApiException {
        OrderItemPojo p = get_id(id);
        int oldQuantity = p.getQuantity();
        int newQuantity = newPojo.getQuantity();
        InventoryPojo inventoryPojo = new InventoryPojo();

//        removing old quantity from old inventory
        inventoryPojo.setId(p.getProductId());
        inventoryPojo.setQuantity(oldQuantity);
        inventoryService.addInventory(inventoryPojo);
//        adding new quantity to new inventory
        inventoryPojo.setId(newPojo.getProductId());
        inventoryPojo.setQuantity(newQuantity);
        inventoryService.reduceInventory(inventoryPojo);

        p.setQuantity(newPojo.getQuantity());
        p.setOrderId(newPojo.getOrderId());
        p.setSellingPrice(newPojo.getSellingPrice());
        p.setProductId(newPojo.getProductId());
    }

    public OrderItemPojo get_productId_orderId(Integer productId, Integer orderId) throws ApiException {
        OrderItemPojo p = dao.selectByProductIdOrderId(productId, orderId);
        if (p == null)
            throw new ApiException("No product found with given combination of product id and order id");
        return p;
    }
}
