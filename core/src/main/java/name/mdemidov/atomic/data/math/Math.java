package name.mdemidov.atomic.data.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Math {

    static double round(double value) {
        return round(value, 2);
    }

    /**
     * Rounds doubles.
     *
     * @param value  to round
     * @param places to keep
     * @return rounded double
     */
    static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException("Wrong argument places=" + places);
        }
        return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
