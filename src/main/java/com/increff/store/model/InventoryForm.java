package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class InventoryForm {
    @Positive
    private Integer quantity;
    @NotBlank
    private String barcode;
}
