package com.loanms.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyUtil {
    private MoneyUtil() { }

    public static String format(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static boolean positive(double value) {
        return value > 0 && Double.isFinite(value);
    }
}
