package com.siddhu.capp.app;

import android.app.Application;

/**
 * Created by baji_g on 1/6/2017.
 */

public class CollegeApplicationClass extends Application {
    public static CollegeApplicationClass mCollegeApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        CollegeApplicationClass.mCollegeApplication = this;
    }

    public static CollegeApplicationClass getInstance() {
        return mCollegeApplication;
    }
}
