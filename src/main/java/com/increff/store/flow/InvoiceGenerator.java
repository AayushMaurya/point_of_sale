package com.increff.store.flow;

import com.increff.store.model.form.InvoiceForm;
import com.increff.store.model.OrderItem;
import com.increff.store.pojo.OrderItemPojo;
import com.increff.store.pojo.OrderPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.OrderItemService;
import com.increff.store.api.OrderService;
import com.increff.store.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceGenerator {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    public InvoiceForm generateInvoiceForOrder(Integer orderId) throws ApiException {
        InvoiceForm invoiceForm = new InvoiceForm();
        OrderPojo orderPojo = orderService.getOrderById(orderId);

        if (!Objects.equals(orderPojo.getStatus(), "Placed"))
            throw new ApiException("The order is not yet placed");

        invoiceForm.setOrderId(orderPojo.getId());
        invoiceForm.setCustomerName(orderPojo.getCustomerName());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        invoiceForm.setPlaceDate(orderPojo.getPlaceDateTime().format(dateTimeFormatter));

        List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(orderPojo.getId());
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemPojo p : orderItemPojoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(p.getId());
            String productName = productService.getProductById(p.getProductId()).getName();
            orderItem.setProductName(productName);
            orderItem.setQuantity(p.getQuantity());
            orderItem.setSellingPrice(p.getSellingPrice());
            orderItemList.add(orderItem);
        }

        invoiceForm.setOrderItemList(orderItemList);

        return invoiceForm;
    }
}