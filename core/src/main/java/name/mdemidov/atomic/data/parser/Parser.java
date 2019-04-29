package name.mdemidov.atomic.data.parser;

import lombok.val;

public interface Parser {

    static int parseInt(String text) {
        val number = text.replaceAll("[^\\d-]", "");
        return Integer.valueOf(number);
    }

    static double parseDouble(String text) {
        val number = text.replaceAll("[^\\d.-]", "");
        return Double.valueOf(number);
    }
}
