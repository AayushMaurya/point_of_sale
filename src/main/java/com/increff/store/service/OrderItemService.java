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
    public void add(OrderItemPojo p) throws ApiException
    {
        int orderId = p.getOrderId();
        OrderPojo orderPojo = orderService.get_id(orderId);
        if(orderPojo == null)
            throw new ApiException("No order found with given order id");

        dao.insert(p);
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(p.getQuantity());
        inventoryPojo.setId(p.getProductId());
        inventoryService.remove(inventoryPojo);
    }

    @Transactional
    public List<OrderItemPojo> get_order(int id)
    {
        return dao.select_order(id);
    }

    @Transactional
    public List<OrderItemPojo> get_all()
    {
        return dao.get_all();
    }

    @Transactional
    public OrderItemPojo get_id(int id) throws ApiException
    {
        return dao.select_ItemId(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete_ItemId(int id) throws ApiException
    {
        OrderItemPojo orderItemPojo = dao.select_ItemId(id);
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(orderItemPojo.getProductId());
        inventoryPojo.setQuantity(orderItemPojo.getQuantity());
        inventoryService.add(inventoryPojo);
        dao.delete_ItemId(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, OrderItemPojo newPojo) throws ApiException
    {
        OrderItemPojo p = get_id(id);
        int oldQuantity = p.getQuantity();
        int newQuantity = newPojo.getQuantity();
        InventoryPojo inventoryPojo = new InventoryPojo();

//        removing old quantity from old inventory
        inventoryPojo.setId(p.getProductId());
        inventoryPojo.setQuantity(oldQuantity);
        inventoryService.add(inventoryPojo);
//        adding new quantity to new inventory
        inventoryPojo.setId(newPojo.getProductId());
        inventoryPojo.setQuantity(newQuantity);
        inventoryService.remove(inventoryPojo);

        p.setQuantity(newPojo.getQuantity());
        p.setOrderId(newPojo.getOrderId());
        p.setSellingPrice(newPojo.getSellingPrice());
        p.setProductId(newPojo.getProductId());
    }
}
