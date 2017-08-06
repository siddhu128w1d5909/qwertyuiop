package com.siddhu.capp.presenter;

import com.siddhu.capp.interactor.RehgistrationInteractor;
import com.siddhu.capp.interactor.RehgistrationInteractorImpl;
import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.error.APIError;

/**
 * Created by baji_g on 8/4/2017.
 */

public class RegistrationPresenterImpl implements RegistrationPresenter,RehgistrationInteractor.RegistarationListener {

    public RegistrationPresenter.RegistrationView mRegistrationView;
    public RehgistrationInteractorImpl rehgistrationInteractor;

    public RegistrationPresenterImpl() {
        rehgistrationInteractor = new RehgistrationInteractorImpl();
    }


    @Override
    public void attachView(RegistrationView view) {
        mRegistrationView = view;

    }

    @Override
    public void detachView() {
        mRegistrationView = null;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        rehgistrationInteractor.register(registerRequest,this);
        if(mRegistrationView != null){
            mRegistrationView.showHideProgress(true);
        }

    }

    @Override
    public void onRegistarationSuccessful(RegisterRequest registerRequest) {

        if (mRegistrationView != null){
            mRegistrationView.showHideProgress(false);
            mRegistrationView.onRegistrationSuccessful(registerRequest);
        }
    }

    @Override
    public void onRegistarationFailed(APIError apiError) {
        if (mRegistrationView != null){
            mRegistrationView.showHideProgress(false);
            mRegistrationView.onRegistrationFailed(apiError);
        }
    }

    @Override
    public void onRegistarationFailed(Throwable t) {
        if (mRegistrationView != null){
            mRegistrationView.showHideProgress(false);
            mRegistrationView.onRegistrationFailed(t);
        }
    }
}
