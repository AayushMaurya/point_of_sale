package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductData {
    private int id;
    private String barcode;
    private int brandCategory;
    private String name;
    private double mrp;
}
