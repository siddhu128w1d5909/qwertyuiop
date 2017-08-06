package com.siddhu.capp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;

/**
 * Created by baji_g on 1/6/2017.
 */
public class HomeActivity extends CollegeMainActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static Intent getHomeIntent(CollegeApplicationClass instance) {
        return new Intent(instance,HomeActivity.class);
    }
}
