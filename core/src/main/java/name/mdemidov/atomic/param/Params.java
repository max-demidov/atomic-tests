package name.mdemidov.atomic.param;

import lombok.val;

import java.util.EnumMap;
import java.util.Map;

public abstract class Params {

    private static final Map<Param, String> PARAMS = new EnumMap<>(Param.class);

    private Params() {
    }

    /**
     * Provides a text value of {@link Param} <code>param</code>.
     *
     * @param param name
     * @return text value of <code>param</code>
     */
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
