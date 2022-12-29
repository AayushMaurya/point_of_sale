package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    private String barcode;
    private int brandCategory;
    private String name;
    private double mrp;

}
