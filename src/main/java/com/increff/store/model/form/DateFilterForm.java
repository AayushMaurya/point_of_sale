package com.increff.store.model.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateFilterForm {
    private String start;
    private String end;
}