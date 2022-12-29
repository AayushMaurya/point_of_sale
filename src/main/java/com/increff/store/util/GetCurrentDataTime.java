package com.increff.store.util;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class GetCurrentDataTime {
    public static String get_current_dat_time() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
