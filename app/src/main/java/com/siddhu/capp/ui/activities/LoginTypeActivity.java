package com.siddhu.capp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.utils.AppConstants;


/**
 * Created by sushe on 5/27/2017.
 */

public class LoginTypeActivity extends AppCompatActivity implements View.OnClickListener {

    public Button HodButton,StaffButton,StudentButton;
    private TextView mHeaderTxt;

    public void init()
    {
        HodButton=(Button)findViewById(R.id.HodButton);
        StaffButton=(Button)findViewById(R.id.StaffButton);
        StudentButton=(Button)findViewById(R.id.StudentButton);
        StaffButton.setOnClickListener(this);
        StudentButton.setOnClickListener(this);
        HodButton.setOnClickListener(this);
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.header_layout);
        headerLayout.setVisibility(View.VISIBLE);
        ImageView backButton = (ImageView) findViewById(R.id.header_back_btn);
        backButton.setVisibility(View.INVISIBLE);
        backButton.setOnClickListener(this);
        mHeaderTxt = (TextView)findViewById(R.id.header_txt);
        mHeaderTxt.setText("Choose Login Type");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_me);
        init();
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.HodButton) {
           Intent loginToHod = new Intent(LoginTypeActivity.this,UserLoginActivity.class);
           loginToHod.putExtra(AppConstants.LOGIN_USER_TYPE, AppConstants.HOD);
           CollegeApplicationClass.getInstance().setmLoginUserType( AppConstants.HOD);
           startActivity(loginToHod);
           LoginTypeActivity.this.finish();
       }else if(v.getId() == R.id.StaffButton){
           Intent loginToHod = new Intent(LoginTypeActivity.this,UserLoginActivity.class);
           CollegeApplicationClass.getInstance().setmLoginUserType( AppConstants.STAFF);
           loginToHod.putExtra(AppConstants.LOGIN_USER_TYPE, AppConstants.STAFF);
           startActivity(loginToHod);
           LoginTypeActivity.this.finish();
       }else if(v.getId() == R.id.StudentButton){
           Intent loginToHod = new Intent(LoginTypeActivity.this,UserLoginActivity.class);
           CollegeApplicationClass.getInstance().setmLoginUserType( AppConstants.STUDENT);
           loginToHod.putExtra(AppConstants.LOGIN_USER_TYPE, AppConstants.STUDENT);
           startActivity(loginToHod);
           LoginTypeActivity.this.finish();
       }else if(v.getId() == R.id.header_back_btn){
           finish();
       }
    }
}
