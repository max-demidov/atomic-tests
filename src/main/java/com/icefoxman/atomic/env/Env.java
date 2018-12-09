package com.icefoxman.atomic.env;

import java.util.Arrays;

public enum Env {
    GOOGLE("https://google.com");

    private String url;

    Env(String url) {
        this.url = url;
    }

    public static String pattern() {
        String[] values = Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
        return String.format("^(%s)$", String.join("|", values));
    }

    public String url() {
        return url;
    }
}
