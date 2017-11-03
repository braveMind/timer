package com.jun.timer.utils.httpClient.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * Created by jun on 16/7/6.
 */


public class GsonProvider implements JsonProvider {

    private Gson gson;

    public Gson getGson() {
        return gson;
    }

    public GsonProvider(Gson gson) {
        this.gson = gson;
    }

    public GsonProvider() {
        this(new GsonBuilder().disableHtmlEscaping().create());
    }


    public void toJson(Writer writer, Object value) {
        gson.toJson(value,writer);

    }

    public <T> T fromJson(Reader reader, Type type) {
        return gson.fromJson(reader,type);
    }
}


