package com.icefoxman.atomic.browser;

import lombok.val;

import java.util.Arrays;

public enum Name {
    CHROME, FIREFOX, EDGE, IE, SAFARI;

    public static String pattern() {
        val values = Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
        return String.format("^(%s)$", String.join("|", values));
    }
}
