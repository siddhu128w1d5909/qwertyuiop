package com.siddhu.capp.utils;

import android.support.design.BuildConfig;
import android.util.Log;


public class Logger {

    private Logger() {

    }
    private static final String TAG = "Babies R Us Logger";

    public static void d(final String text) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void e(final String text) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, text);
        }
    }

    public static void v(final String text) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, text);
        }
    }

    public static void w(final String text) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void i(final String text) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void d(final String mTag, final String text) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void e(final String mTag, final String text) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, text);
        }
    }

    public static void v(final String mTag, final String text) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, text);
        }
    }

    public static void w(final String mTag, final String text) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void i(final String mTag, final String text) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void log(final String mTag, String e) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, String.valueOf(e));
        }
    }
    public static void log(final String mTag, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, String.valueOf(e));
            e.printStackTrace();
        }
    }
}
