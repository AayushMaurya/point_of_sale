package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;
}
