package com.siddhu.capp.ui.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.siddhu.capp.R;
import com.siddhu.capp.ui.fragments.LoginFragment;
import com.siddhu.capp.ui.fragments.ProfileFragment;
import com.siddhu.capp.ui.fragments.RegisterFragment;
import com.siddhu.capp.ui.fragments.ResetPasswordFragment;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.Utility;


/**
 * Created by siddhu on 4/1/2017.
 */

public class StudentActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private SharedPreferences pref;
    private Toolbar toolbar;
    private String fragmentTag;
    Bundle bundle;
    Intent intent;
    public static  String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);
        //bundle = intent.getExtras();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            User = extras.getString(AppConstants.LOGIN_USER_TYPE);
            //The key argument here must match that used in the other activity
        }
        initToolBar();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        pref = getPreferences(0);
        initFragment();
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(User+" Login");

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        handleonBackpressed();
                    }
                }

        );
    }

    private void handleonBackpressed() {
        onBackStackChanged();
    }


    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(AppConstants.IS_LOGGED_IN,false)){
            fragment = new ProfileFragment();
            fragmentTag = "ProfileFragment";
        }else {
            fragment = new LoginFragment();
            fragmentTag = "LoginFragment";
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add( R.id.fragment_frame,fragment,fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackStackChanged();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackStackChanged() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_frame);
        if (currentFragment instanceof RegisterFragment) {
            Fragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,login);
            ft.addToBackStack("LoginFragment");
            ft.commit();
        }
        else if(currentFragment instanceof ResetPasswordFragment){
            Fragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,login);
            ft.addToBackStack("LoginFragment");
            ft.commit();
        }else if(currentFragment instanceof LoginFragment){
            String message = "Are you sure you want to exit?";
            Utility.showAlertMessage(this,message,"Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    StudentActivity.this.finish();
                }
            });
           // Toast.makeText(this,"onback",Toast.LENGTH_LONG).show();

        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        onBackStackChanged();
        //super.onBackPressed();
    }
}
