package com.siddhu.capp.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Utility class contains methods to parse json data to generic class type and converts
 * json object to json string.
 */
public final class GsonUtil {

    private GsonUtil() {
        // hide constructor
    }

    /**
     * Parses the json string to json object of generic class type
     * and returns it.
     *
     * @param json JSON string
     * @param classOfT Generic class which will store the parsed gson data.
     * @param <T>  Class type
     * @return Returns the parsed json object
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        T retObj;
        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            retObj = null;
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        return retObj;
    }

    /**
     * arses the json string to json object of generic class type
     * and returns it.
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        T retObj;

        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, type);
        } catch (Exception e) {
            retObj = null;
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        return retObj;
    }

    /**
     * arses the json string to json object of generic class type
     * and returns it.
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(Reader json, Type type) {
        T retObj;

        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, type);
        } catch (Exception e) {
            retObj = null;
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        return retObj;
    }

    /**
     * arses the json string to json object of generic class type
     * and returns it.
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(Reader json, Class<T> classOfT) {
        T retObj;
        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            retObj = null;
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        return retObj;
    }

    /**
     * Converts the json object to a string object and returns it.
     *
     * @param obj json object
     * @return Returns a string
     */
    public static String toJson(Object obj) {
        String retJsonString = "";
        final Gson gson = new Gson();

        try {
            retJsonString = gson.toJson(obj);
        } catch (Exception e) {
            retJsonString = null;
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }
        return retJsonString;
    }

    /**
     * gets String From jsonInput Reader
     * @param jsonInput
     * @return string values
     */
    private static String getStringFromReader(Reader jsonInput) {
        BufferedReader br;
        final StringBuilder sb = new StringBuilder("");
        String line;
        try {
            br = new BufferedReader(jsonInput);
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            Log.e(AppConstants.EXCPTN_TAG, e.getMessage(), e);
        }

        return sb.toString();

    }
}
