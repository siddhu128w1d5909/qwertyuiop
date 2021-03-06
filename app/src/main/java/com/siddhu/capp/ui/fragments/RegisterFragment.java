package com.siddhu.capp.ui.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.UrlUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddhu on 4/1/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_register;
    private EditText et_email, et_password, et_name;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        btn_register = (AppCompatButton) view.findViewById(R.id.btn_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Student Registration");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name, email, password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService requestInterface = retrofit.create(ApiService.class);


        Student user = new Student();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(AppConstants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Log.d(AppConstants.TAG, resp.getMessage());
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                if(resp.getMessage().toString().startsWith("User"))
                {
                    progress.setVisibility(View.INVISIBLE);
                  return;
                }else{
                    goToLogin();
                }

                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(AppConstants.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.addToBackStack("Register");
        ft.commit();
    }


}