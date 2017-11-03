package com.jun.timer.utils.httpClient.json;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Objects;

/**
 * Created by jun on 16/7/6.
 */
public class JsonFactory {
    Log logger= LogFactory.getLog(JsonFactory.class);
    private JsonProvider jsonProvider;
    private static JsonFactory jsonFactory=new JsonFactory();


    private JsonFactory() {

    }

    public static JsonFactory getInstance(){
        return jsonFactory;
    }

    public synchronized void setJsonProvider(JsonProvider jsonProvider) {
        this.jsonProvider = Objects.requireNonNull(jsonProvider);

    }
    private boolean hasGson() {
        try {
            Class.forName("com.google.gson.Gson");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    private JsonProvider gsonProvider(){

        return new GsonProvider();
    }
    public synchronized JsonProvider lookup() {
        if (jsonProvider != null) {
            return jsonProvider;
        }

        if (hasGson()) {
            logger.info("Use default gson provider to deal with json");
            jsonProvider = gsonProvider();
            return jsonProvider;
        }
        throw new RuntimeException("No json provider found");
    }

}
