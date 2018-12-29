package com.icefoxman.atomic.env;

import lombok.val;

import java.util.Arrays;

public enum Env {
    GOOGLE("https://google.com");

    private String url;

    Env(String url) {
        this.url = url;
    }

    public static String pattern() {
        val values = Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
        return String.format("^(%s)$", String.join("|", values));
    }

    public String url() {
        return url;
    }
}
