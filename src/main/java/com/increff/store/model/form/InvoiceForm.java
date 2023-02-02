package com.increff.store.model.form;

import com.increff.store.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceForm {
    private Integer orderId;
    private String customerName;
    private String placeDate;
    private List<OrderItem> orderItemList;
}
