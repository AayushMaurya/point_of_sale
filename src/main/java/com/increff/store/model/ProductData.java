package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData {
    private Integer id;
    private String barcode;
    private Integer brandCategory;
    private String brand;
    private String category;
    private String name;
    private double mrp;
}
