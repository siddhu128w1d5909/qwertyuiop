package com.siddhu.capp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;


import com.siddhu.capp.models.login.LoginResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by dhiman_da on 8/2/2016.
 */
public class SessionUtils {



    private SessionUtils() {

    }


    private static final String SessionSharedPreferenceName = "session_preference_name";
    private static final String SessionEmailPreferenceKey = "session_email_preference_key";
    private static final String SessionPasswordSharedPreferenceKey = "session_password_key";
    private static final String SessionTokenSharedPreferenceKey = "session_token_key";
    public static void saveSession(@NonNull Context context, @NonNull LoginResponse response) {
        Logger.d("Save Session : " + response.toString());

        final SharedPreferences sharedPreferences = context.getSharedPreferences(SessionSharedPreferenceName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( SessionEmailPreferenceKey, response.getEmail());
        editor.putString(SessionPasswordSharedPreferenceKey, response.getPassword());
        editor.putString(SessionTokenSharedPreferenceKey,response.getToken());
        editor.apply();
    }


    public static void clearSession(@NonNull Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(SessionSharedPreferenceName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
