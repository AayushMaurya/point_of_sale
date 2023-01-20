package com.increff.store.dto;

import com.increff.store.model.OrderItemData;
import com.increff.store.model.OrderItemForm;
import com.increff.store.model.UpdateOrderItemForm;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.pojo.ProductPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.OrderItemService;
import com.increff.store.service.OrderService;
import com.increff.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderItemDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService service;

    @Autowired
    private OrderService orderService;

    public void addOrderItem(OrderItemForm form) throws ApiException {
        OrderItemPojo p = convert(form);

        OrderItemPojo oldPojo = service.getProductIdOrderId(p.getProductId(), p.getOrderId());
        if (oldPojo != null) {
            Integer q = p.getQuantity();
            p.setQuantity(oldPojo.getQuantity() + q);
            service.updateOrderItem(oldPojo.getId(), p);
        } else {
            service.addOrderItem(p);
        }
    }

    public void updateOrderItem(UpdateOrderItemForm form) throws ApiException {
        OrderItemPojo newPojo = new OrderItemPojo();
        newPojo.setId(form.getId());
        newPojo.setOrderId(form.getOrderId());
        newPojo.setProductId(form.getProductId());
        newPojo.setQuantity(form.getQuantity());
        newPojo.setSellingPrice(form.getMrp());
        service.updateOrderItem(form.getId(), newPojo);
    }

    public List<OrderItemData> getOrderItemByOrderId(Integer id) throws ApiException {
        List<OrderItemPojo> list1 = service.getOrder(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();

        for (OrderItemPojo p : list1)
            list2.add(convert(p));

        return list2;
    }

    public List<OrderItemData> getAllOrderItem() throws ApiException {
        List<OrderItemPojo> list1 = service.getAllOrderItem();
        List<OrderItemData> list2 = new ArrayList<>();

        for (OrderItemPojo p : list1)
            list2.add(convert(p));

        return list2;
    }

    private OrderItemPojo convert(OrderItemForm form) throws ApiException {
        OrderItemPojo p = new OrderItemPojo();
        p.setOrderId(form.getOrderId());
        p.setQuantity(form.getQuantity());
        p.setSellingPrice(form.getSellingPrice());
        String code = form.getBarCode();
        ProductPojo productPojo = productService.getProductByBarcode(code);
        p.setProductId(productPojo.getId());
        p.setBrandCategory(productPojo.getBrandCategory());
        return p;
    }

    private OrderItemData convert(OrderItemPojo p) throws ApiException {
        OrderItemData d = new OrderItemData();
        d.setId(p.getId());
        ;
        d.setOrderId(p.getOrderId());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        String productName = productService.getProductById(p.getProductId()).getName();
        d.setProductName(productName);
        d.setProductId(p.getProductId());
        return d;
    }

}
