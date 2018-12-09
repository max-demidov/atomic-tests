package com.icefoxman.atomic.browser;

import java.util.Arrays;

public enum Name {
    CHROME, FIREFOX, EDGE, IE, SAFARI;

    public static String pattern() {
        String[] values = Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
        return String.format("^(%s)$", String.join("|", values));
    }
}
