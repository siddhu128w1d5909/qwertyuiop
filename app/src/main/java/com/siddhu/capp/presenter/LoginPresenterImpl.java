package com.siddhu.capp.presenter;
import com.siddhu.capp.interactor.LoginInteractor;
import com.siddhu.capp.interactor.LoginInteractorImpl;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

/**
 * Created by baji_g on 1/6/2017.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginInteractor.LoginListener {

    private LoginView mLoginView;
    private LoginInteractorImpl mLoginInteractorImpl;

    public LoginPresenterImpl() {
        mLoginInteractorImpl = new LoginInteractorImpl();
    }

    @Override
    public void attachView(LoginView view) {
        mLoginView = view;

    }

    @Override
    public void detachView() {
        mLoginView = null;

    }

    @Override
    public void login(String email, String password) {
        mLoginInteractorImpl.login(email,password,this);
        if(mLoginView != null){
            mLoginView.showHideProgress(true);
        }

    }

    @Override
    public void onLoginSuccessful(LoginResponse loginResponse) {
        if(mLoginView != null){
            mLoginView.showHideProgress(false);
            mLoginView.onLoginSuccessful(loginResponse);
        }

    }

    @Override
    public void onLoginFailed(APIError apiError) {
        if(mLoginView != null){
            mLoginView.showHideProgress(false);
            mLoginView.onLoginFailed(apiError);
        }

    }

    @Override
    public void onLoginFailed(Throwable t) {
        if(mLoginView != null){
            mLoginView.showHideProgress(false);
            mLoginView.onLoginFailed(t);
        }

    }
}
