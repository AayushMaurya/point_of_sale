package com.increff.store.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderItemForm {
    private Integer orderItemId;
    private Double sellingPrice;
    private Integer quantity;
}
