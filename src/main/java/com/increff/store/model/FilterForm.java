package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterForm extends DateFilterForm{
    private String brand;
    private String category;
}
