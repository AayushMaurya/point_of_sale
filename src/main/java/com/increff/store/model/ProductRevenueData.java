package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRevenueData {
    private Integer id;
    private String barcode;
    private String name;
    private String brand;
    private String category;
    private double mrp;
    private Integer quantity;
    private double total;

}
