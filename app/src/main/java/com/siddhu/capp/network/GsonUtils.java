package com.siddhu.capp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baji_g on 1/6/2017.
 */

public class GsonUtils {
    public  static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(SpellCheck.class, new SpellCheckResponseDeserializer());
        Gson gson = gsonBuilder.create();//GsonConverterFactory.create()
        return GsonConverterFactory.create(gson);
    }
}
