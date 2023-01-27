package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportModel {
    private Integer brandCategoryId;
    private String brand;
    private String category;
    private Integer quantity;
}
