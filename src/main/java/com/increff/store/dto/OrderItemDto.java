package com.increff.store.dto;

import com.google.protobuf.Api;
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
import com.increff.store.util.StringUtil;
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
        checkOrderItemForm(form);

        OrderItemPojo pojo = convert(form);

        OrderItemPojo oldPojo = service.getProductIdOrderId(pojo.getProductId(), pojo.getOrderId());
        if (oldPojo != null) {
            Integer q = pojo.getQuantity();
            pojo.setQuantity(oldPojo.getQuantity() + q);
            service.updateOrderItem(oldPojo.getId(), pojo);
        } else {
            service.addOrderItem(pojo);
        }
    }

    public void updateOrderItem(UpdateOrderItemForm form) throws ApiException {
        checkUpdateOrderItemForm(form);
        OrderItemPojo newPojo = service.getOrderItemById(form.getOrderItemId());
        if (checkIfOrderPlaced(newPojo.getOrderId()))
            throw new ApiException("Cannot update item in placed order");
        newPojo.setQuantity(form.getQuantity());
        newPojo.setSellingPrice(form.getSellingPrice());
        service.updateOrderItem(form.getOrderItemId(), newPojo);
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
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setOrderId(form.getOrderId());
        pojo.setQuantity(form.getQuantity());
        pojo.setSellingPrice(form.getSellingPrice());
        String code = form.getBarCode();
        ProductPojo productPojo = productService.getProductByBarcode(code);
        if (form.getSellingPrice() > productPojo.getMrp())
            throw new ApiException("Selling price cannot be greater than mrp");
        pojo.setProductId(productPojo.getId());
        pojo.setBrandCategory(productPojo.getBrandCategory());
        return pojo;
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

    private boolean checkIfOrderPlaced(Integer orderId) throws ApiException {
        OrderPojo orderPojo = orderService.getOrderById(orderId);
        if (Objects.equals(orderPojo.getStatus(), "Placed"))
            return true;
        return false;
    }

    protected static void checkUpdateOrderItemForm(UpdateOrderItemForm form) throws ApiException {
        if (!StringUtil.isPositive(form.getSellingPrice()))
            throw new ApiException("Input valid Selling Price");
        if (!StringUtil.isPositive(form.getQuantity()))
            throw new ApiException("Input valid quantity");
        form.setSellingPrice(StringUtil.normalizeDouble(form.getSellingPrice()));
    }

    private void checkOrderItemForm(OrderItemForm form) throws ApiException
    {
        OrderPojo orderPojo = orderService.getOrderById(form.getOrderId());
        if(checkIfOrderPlaced(orderPojo.getId()))
            throw new ApiException("Cannot add item in placed order");

        if (!StringUtil.isPositive(form.getSellingPrice()))
            throw new ApiException("Input valid Selling Price");
        if (!StringUtil.isPositive(form.getQuantity()))
            throw new ApiException("Input valid quantity");

        form.setSellingPrice(StringUtil.normalizeDouble(form.getSellingPrice()));
    }
}
