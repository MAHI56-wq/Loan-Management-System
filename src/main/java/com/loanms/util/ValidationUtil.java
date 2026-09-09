package com.loanms.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private static final Pattern PHONE =
            Pattern.compile("^[+0-9][0-9 -]{6,19}$");

    private ValidationUtil() {
    }

    public static void requireText(String value, String field) {

        if (value == null || value.isBlank()) {

            throw new IllegalArgumentException(
                    field + " is required"
            );
        }
    }

    public static boolean isEmail(String value) {

        return value != null
                && EMAIL.matcher(value.trim()).matches();
    }

    public static boolean isPhone(String value) {

        return value != null
                && PHONE.matcher(value.trim()).matches();
    }

    public static void requireRange(
            double value,
            double min,
            double max,
            String field
    ) {

        if (!Double.isFinite(value)
                || value < min
                || value > max) {

            throw new IllegalArgumentException(
                    field + " must be between "
                            + min
                            + " and "
                            + max
            );
        }
    }
}