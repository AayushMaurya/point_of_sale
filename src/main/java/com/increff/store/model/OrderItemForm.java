package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    private Integer orderId;
    private String barCode;
    private Integer quantity;
    private double sellingPrice;
}
