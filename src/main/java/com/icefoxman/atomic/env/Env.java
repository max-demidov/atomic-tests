package com.icefoxman.atomic.env;

import lombok.Getter;
import lombok.val;

import java.util.Arrays;

@Getter
public enum Env {
    GOOGLE("google.com");

    private String domain;
    private String startUrl;

    Env(String domain) {
        this.domain = domain;
        this.startUrl = String.format("https://%s", domain);
    }

    public static String pattern() {
        val values = Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
        return String.format("^(%s)$", String.join("|", values));
    }
}
