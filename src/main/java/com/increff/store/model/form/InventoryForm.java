package com.increff.store.model.form;

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
