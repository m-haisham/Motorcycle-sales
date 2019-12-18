package com.cerberus.models.helpers;

import com.cerberus.models.customer.event.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GsonHelper {

    private GsonHelper() {}

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Event.class, new InterfaceAdapter());
        return builder.create();
    }

    public static void createArrayFile(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("[]");
        fileWriter.close();
    }

}
