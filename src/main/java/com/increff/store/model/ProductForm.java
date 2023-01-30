package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm extends UpdateProductForm {
    private String barcode;
    private String brandName;
    private String categoryName;

}
