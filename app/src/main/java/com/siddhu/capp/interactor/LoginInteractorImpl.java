package com.siddhu.capp.interactor;

import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginRequest;
import com.siddhu.capp.models.login.LoginResponse;
import com.siddhu.capp.network.CollgeRestApi;
import com.siddhu.capp.network.RestClient;
import com.siddhu.capp.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by baji_g on 1/6/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(String email, String password, final LoginListener loginListener) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        final Retrofit retrofit = RestClient.getRetrofit();
        final CollgeRestApi demoRestApi = retrofit.create(CollgeRestApi.class);
        final Call<LoginResponse> call = demoRestApi.getLogin(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    loginListener.onLoginSuccessful(response.body());
                }
                else {
                    final APIError apiError = ErrorUtils.parseError(response);
                    loginListener.onLoginFailed(apiError);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginListener.onLoginFailed(t);
            }
        });

    }
}
