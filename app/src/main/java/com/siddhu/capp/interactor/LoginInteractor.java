package com.siddhu.capp.interactor;


import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface LoginInteractor {

    void login(String email, String password, LoginListener loginListener);

    interface LoginListener{

        void onLoginSuccessful(LoginResponse loginResponse);

        void onLoginFailed(APIError apiError);

        void onLoginFailed(Throwable t);

    }
}
