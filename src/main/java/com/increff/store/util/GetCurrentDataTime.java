package com.increff.store.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GetCurrentDataTime {
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }
}
