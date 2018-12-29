package com.icefoxman.atomic.param;

import lombok.val;

import java.util.EnumMap;
import java.util.Map;

public abstract class Params {

    private static final Map<Param, String> PARAMS = new EnumMap<>(Param.class);

    public static String get(Param param) {
        if (!PARAMS.containsKey(param)) {
            val value = System.getProperty(param.type(), param.defaultValue());
            if (value != null) {
                param.validate(value);
            }
            PARAMS.put(param, value);
        }
        return PARAMS.get(param);
    }
}
