package com.cerberus.models.helpers;

public class StringHelper {

    private StringHelper() {}

    public static String create(String character, int length) {
        return (new String(new char[length])).replace("\0", character);
    }

    public static String padded(String string, String padding) {
        return padding + string;
    }
}
