package com.cerberus.models.helpers;

public class StringHelper {

    private StringHelper() {}

    public static String create(String character, int length) {
        return (new String(new char[length])).replace("\0", character);
    }

    public static String padded(String string, String padding) {
        return padding + string;
    }

    public static int width = 100;

    public static String getSpacer(int width, String leading, String trailing) {
        return StringHelper.create(" ", width - leading.length() - trailing.length());
    }

    public static String bullet() {
        return "\u2022";
    }

    public static String formatMoney(double amount) {
        String parsed = String.format("%.2f", amount);

        int count = -2;
        for (int i = parsed.length() - 1; i >= 0; i--) {

            if (count >= 3 && i != 0) {
                // insert comma
                parsed = parsed.substring(0, i) + "," + parsed.substring(i);
                count = 0;
            }

            count++;
        }

        return parsed;

    }
}
