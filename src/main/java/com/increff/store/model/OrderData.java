package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData extends OrderForm {
    private Integer id;
    private String createdDataTime;
    private String placedDataTime;
    private String status;
    private String orderCode;
}
