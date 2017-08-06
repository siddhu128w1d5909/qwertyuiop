package com.siddhu.capp.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.siddhu.capp.R;
import com.siddhu.capp.models.ServerRequest;
import com.siddhu.capp.models.ServerResponse;
import com.siddhu.capp.models.Student;
import com.siddhu.capp.network.ApiService;
import com.siddhu.capp.ui.activities.DashBoardActivity;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.PreferenceUtil;
import com.siddhu.capp.utils.UrlUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddhu on 4/1/2017.
 */

public class LoginFragment  extends Fragment implements View.OnClickListener{

    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register,tv_forgotpwd,tv_dash;
    private ProgressBar progress;
    private SharedPreferences pref;
    private String User = "Hod";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_login,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        pref = getActivity().getPreferences(0);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            User = extras.getString(AppConstants.LOGIN_USER_TYPE);
            //The key argument here must match that used in the other activity
        }
       /* btn_login = (AppCompatButton)view.findViewById(R.id.btn_login);
        tv_register = (TextView)view.findViewById(R.id.tv_register);
        tv_forgotpwd = (TextView)view.findViewById(R.id.tv_forgotpwd);
        tv_dash = (TextView)view.findViewById(R.id.tv_dash);*/
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);

        progress = (ProgressBar)view.findViewById(R.id.progress);
        if(User != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(User + " Login");
        }else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" Login");
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forgotpwd.setOnClickListener(this);
        tv_dash.setOnClickListener(this);
        tv_dash.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

           /* case R.id.tv_register:
                goToRegister();
                break;
            case R.id.tv_forgotpwd:
                goToResetPassword();
                break;
            case R.id.tv_dash:
                PreferenceUtil.getInstance(getActivity()).saveStringParam(AppConstants.LOGIN_USER_TYPE,User);
                startActivity(new Intent(getActivity(), DashBoardActivity.class));
                getActivity().finish();
                break;

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    loginProcess(email,password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }*/
        }
    }
    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService requestInterface = retrofit.create(ApiService.class);

        Student user = new Student();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(AppConstants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(AppConstants.SUCCESS)){
                    PreferenceUtil.getInstance(getActivity()).saveStringParam(AppConstants.LOGIN_USER_TYPE,User);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(AppConstants.IS_LOGGED_IN,true);
                    editor.putString(AppConstants.EMAIL,resp.getUser().getEmail());
                    editor.putString(AppConstants.NAME,resp.getUser().getName());
                    editor.putString(AppConstants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.apply();
                    startActivity(new Intent(getActivity(), DashBoardActivity.class));

                    getActivity().finish();
                   // goToProfile();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(AppConstants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void goToRegister(){

        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,register);
        ft.addToBackStack(register.getTag());
        ft.commit();
    }

    private void goToProfile(){

        Fragment profile = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,profile);
        ft.commit();
    }

    private void goToResetPassword(){

        Fragment reset = new ResetPasswordFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,reset);
        ft.commit();
    }
}