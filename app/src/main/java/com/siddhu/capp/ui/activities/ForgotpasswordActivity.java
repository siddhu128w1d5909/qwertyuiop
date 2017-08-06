package com.siddhu.capp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.siddhu.capp.R;

/**
 * Created by baji_g on 8/3/2017.
 */
public class ForgotpasswordActivity extends CollegeMainActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
    public static Intent getForgotPwdIntent(@NonNull Context context){
        Intent lIntent = new Intent(context, ForgotpasswordActivity.class);
        lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(lIntent);
        return lIntent;
    }
}
