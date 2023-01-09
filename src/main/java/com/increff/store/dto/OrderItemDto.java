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

    public void add(OrderItemForm form) throws ApiException
    {
        OrderItemPojo p = convert(form);
        OrderItemPojo newPojo = service.get_productId_orderId(p.getProductId(), p.getOrderId());
        if(newPojo != null) {
            int q = p.getQuantity();
            p.setQuantity(newPojo.getQuantity() + q);
            service.update(newPojo.getId(), p);
        }
        else
            service.add(p);
    }

    public void update(UpdateOrderItemForm form) throws ApiException
    {
        OrderItemPojo newPojo = new OrderItemPojo();
        newPojo.setId(form.getId());
        newPojo.setOrderId(form.getOrderId());
        newPojo.setProductId(form.getProductId());
        newPojo.setQuantity(form.getQuantity());
        newPojo.setSellingPrice(form.getMrp());
        service.update(form.getId(), newPojo);
    }

    public List<OrderItemData> get_orderId(int id)
    {
        List<OrderItemPojo> list1 = service.get_order(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();

        for(OrderItemPojo p : list1)
            list2.add(convert(p));

        return list2;
    }

    public List<OrderItemData> get_orderCode(String orderCode) {
        OrderPojo p = orderService.get_order_orderCode(orderCode);
        int id = p.getId();

        return get_orderId(id);
    }

    private OrderItemPojo convert(OrderItemForm form) throws ApiException
    {
       OrderItemPojo p = new OrderItemPojo();
       p.setOrderId(form.getOrderId());
       p.setQuantity(form.getQuantity());
       p.setSellingPrice(form.getSellingPrice());
       String code = form.getBarCode();
       ProductPojo productPojo = productService.get_barcode(code);
       if(productPojo == null)
           throw new ApiException("No product in productPojo exist with given bar code: " + code);
       p.setProductId(productPojo.getId());
       p.setBrandCategory(productPojo.getBrandCategory());
       return p;
    }

    private OrderItemData convert(OrderItemPojo p)
    {
        OrderItemData d = new OrderItemData();
        d.setId(p.getId());;
        d.setOrderId(p.getOrderId());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        d.setProductId(p.getProductId());
        return d;
    }

    public List<OrderItemData> get_all() {
        List<OrderItemPojo> list1 = service.get_all();
        List<OrderItemData> list2 = new ArrayList<>();

        for(OrderItemPojo p :list1)
            list2.add(convert(p));

        return list2;
    }

}
