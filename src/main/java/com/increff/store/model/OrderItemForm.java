package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    private int orderId;
    private String barCode;
    private int quantity;
    private double sellingPrice;
}
