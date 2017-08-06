package com.siddhu.capp.presenter;


import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface LoginPresenter {

    void  attachView(LoginView view);

    void detachView();

    void login(String email, String password);

    interface LoginView{
        void onLoginSuccessful(LoginResponse response);

        void onLoginFailed(APIError apiError);

        void onLoginFailed(Throwable throwable);

        void showHideProgress(boolean show);
    }
}
