package com.siddhu.capp.app;

import android.app.Application;

/**
 * Created by baji_g on 1/6/2017.
 */

public class CollegeApplicationClass extends Application {
    public static CollegeApplicationClass mCollegeApplication;
    public String mLoginUserType;
    @Override
    public void onCreate() {
        super.onCreate();
        CollegeApplicationClass.mCollegeApplication = this;
    }

    public String getmLoginUserType() {
        return mLoginUserType;
    }

    public void setmLoginUserType(String mLoginUserType) {
        this.mLoginUserType = mLoginUserType;
    }

    public static CollegeApplicationClass getInstance() {
        return mCollegeApplication;
    }
}
