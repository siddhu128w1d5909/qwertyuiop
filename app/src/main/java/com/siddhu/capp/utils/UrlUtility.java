package com.siddhu.capp.utils;

import com.siddhu.capp.app.CollegeApplicationClass;

import java.util.HashMap;
import java.util.Map;


public class UrlUtility {
    public static String BASE_URL = "http://192.168.100.6:3080/collegeAdmistration/";
    public static Map<String, String> getCommonRequestHeaders() {
        Map<String, String> collegeHeader = new HashMap<String, String>();
        //collegeHeader.put(APP_LOCALE, getAppLocale());
        collegeHeader.put("Content-Type", "application/json");
        collegeHeader.put("token",PreferenceUtil.getInstance(CollegeApplicationClass.getInstance()).getStringParam(AppConstants.SESSION_TOKEN) );
       // collegeHeader.put(API_KEY, UrlUtility.API_KEY_VALUE);
        //collegeHeader.put(APP_VERSION, "1.0");
        //collegeHeader.put(DEVICE_TYPE, "android");
        return collegeHeader;
    }


    private UrlUtility() {

    }

    public static String getRegistrationUrl() {
        return BASE_URL + "/registers";
    }

    public static String getLoginUrl() {
        return BASE_URL + "/login";
    }


}
