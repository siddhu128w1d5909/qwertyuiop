package com.siddhu.capp.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.UrlUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddhu on 4/1/2017.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password,btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_dashboard,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        tv_name.setText("Welcome : "+pref.getString(AppConstants.NAME,""));
        tv_email.setText(pref.getString(AppConstants.EMAIL,""));

    }

    private void initViews(View view){

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        btn_change_password = (AppCompatButton)view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Student Dashoard");
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(AppConstants.EMAIL,""),old_password,new_password);

                }else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AppConstants.IS_LOGGED_IN,false);
        editor.putString(AppConstants.EMAIL,"");
        editor.putString(AppConstants.NAME,"");
        editor.putString(AppConstants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.addToBackStack(login.getTag());
        ft.commit();
    }

    private void changePasswordProcess(String email,String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService requestInterface = retrofit.create(ApiService.class);

        Student user = new Student();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(AppConstants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(AppConstants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(AppConstants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());

            }
        });
    }
}