package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderItemForm {
    private int id;
    private int quantity;
    private int productId;
    private int orderId;
    private double mrp;
}
