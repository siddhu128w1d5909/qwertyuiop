package com.siddhu.capp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;



public class PreferenceUtil {
    private static final String TAG = PreferenceUtil.class.getSimpleName();
    private static ProgressDialog mProgressDialog;
    public static final String FROM_CO_REGISTRATION = "from_registration_flow";

    public static final String REGISTRATION_OBJECT_KEY = "registration_object";
    public static final String IS_FIRST_TIME = "IS_FIRST_TIME";

    private static PreferenceUtil preferenceUtils = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shareEditor;

    private PreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.FILE_NAME, Context.MODE_PRIVATE);
        shareEditor = sharedPreferences.edit();
    }

    public static PreferenceUtil getInstance(Context context) {
        if (preferenceUtils == null) {
            preferenceUtils = new PreferenceUtil(context);
        }
        return preferenceUtils;
    }

    public void saveBooleanParam(String key, boolean value) {
        shareEditor.putBoolean(key, value).commit();
    }


    public boolean getBooleanParam(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
    /**
     * Returns the corresponding string value stored in the shared preference
     * w.r.t the input key.
     *
     * @param key The key for extracting data from the preferences
     * @return
     */
    public String getStringParam(String key) {
        String value =  sharedPreferences.getString(key, null);
        if (value != null) {
            return EncryptionUtils.decrypt(EncryptionUtils.KEY_ONE, value);
        }
        return value;
    }

    /**
     * Takes the preference key and string value as input, saves it in the
     * shared preference.
     *
     * @param key   The key for saving the preferences
     * @param value string value w.r.t the key
     */
    public void saveStringParam(String key, String value) {
        shareEditor.putString(key, EncryptionUtils.encrypt(EncryptionUtils.KEY_ONE, value)).apply();
    }

    public static void showProgressDialog(Context context, String msg) {
        try {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } catch (Exception e) {
            Logger.log(TAG,e);

        }
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, "Please wait...");
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            Logger.log(TAG,e);

        }
    }



}
