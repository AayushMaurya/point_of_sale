package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderItemForm {
    private Integer id;
    private Integer quantity;
    private Integer productId;
    private Integer orderId;
    private double mrp;
}
