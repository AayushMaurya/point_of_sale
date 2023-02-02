package com.increff.store.model.form;

import com.increff.store.model.form.DateFilterForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterForm extends DateFilterForm {
    private String brand;
    private String category;
}
