package com.increff.store.flow;

import com.increff.store.pojo.InventoryPojo;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.InventoryService;
import com.increff.store.service.OrderItemService;
import com.increff.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemFlow {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    public Integer addOrderItem(OrderItemPojo pojo) throws ApiException
    {
        OrderPojo orderPojo = orderService.getOrderById(pojo.getOrderId());

        Integer id = orderItemService.addOrderItem(pojo);

//        reducing inventory
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setQuantity(pojo.getQuantity());
        inventoryPojo.setId(pojo.getProductId());
        inventoryService.reduceInventory(inventoryPojo);

        return id;
    }

    public void updateOrderItem(Integer id, OrderItemPojo pojo) throws ApiException
    {
        OrderItemPojo oldPojo = orderItemService.getOrderItemById(id);
        Integer oldQuantity = oldPojo.getQuantity();
        Integer newQuantity = pojo.getQuantity();
        InventoryPojo inventoryPojo = new InventoryPojo();

//        adding old quantity to old inventory
        inventoryPojo.setId(oldPojo.getProductId());
        inventoryPojo.setQuantity(oldQuantity);
        inventoryService.addInventory(inventoryPojo);
//        removing new quantity to new inventory
        inventoryPojo.setId(pojo.getProductId());
        inventoryPojo.setQuantity(newQuantity);
        inventoryService.reduceInventory(inventoryPojo);

        orderItemService.updateOrderItem(id, pojo);
    }

    public void deleteOrderItemById(Integer id) throws ApiException
    {
        OrderItemPojo orderItemPojo = orderItemService.getOrderItemById(id);

//        updating the inventory
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(orderItemPojo.getProductId());
        inventoryPojo.setQuantity(orderItemPojo.getQuantity());
        inventoryService.addInventory(inventoryPojo);

        orderItemService.deleteOrderItemById(id);
    }
}
