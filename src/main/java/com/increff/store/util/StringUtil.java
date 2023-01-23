package com.increff.store.util;

import java.text.DecimalFormat;

public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String toLowerCase(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    public static boolean isPositive(Double sellingPrice) {
        if(sellingPrice > 0)
            return true;
        return false;
    }

    public static boolean isPositive(Integer i) {
        if(i > 0)
            return true;
        return false;
    }

    public static Double normalizeDouble(Double sellingPrice) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        return Double.parseDouble(formatter.format(sellingPrice));
    }
}
