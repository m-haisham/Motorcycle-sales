package com.cerberus.models.helpers;

import java.io.IOException;

public class InputHelper {

    private InputHelper() {}

    public static void pause() {
        pause("");
    }

    public static void pause(String message) {
        System.out.print(message);
        try {
            System.in.read();
        } catch (IOException ignored) {}
    }

}
