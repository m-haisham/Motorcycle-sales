package com.cerberus.models.helpers;

import com.cerberus.models.customer.event.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

    private GsonHelper() {}

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Event.class, new InterfaceAdapter());
        return builder.create();
    }

}
