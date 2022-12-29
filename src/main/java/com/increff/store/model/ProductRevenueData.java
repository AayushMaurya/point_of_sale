package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRevenueData {
    private int id;
    private String barcode;
    private String name;
    private double mrp;
    private int quantity;
    private double total;

}
