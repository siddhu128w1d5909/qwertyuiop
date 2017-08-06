package com.siddhu.capp.presenter;
import com.siddhu.capp.interactor.CircularsInteractor;
import com.siddhu.capp.interactor.CircularsInteractorImpl;
import com.siddhu.capp.interactor.LoginInteractor;
import com.siddhu.capp.interactor.LoginInteractorImpl;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public class CircularsPresenterImpl implements CircularsPresenter,CircularsInteractor.CircularsListener {

    private CircularsPresenter.CircularsView mCircularView;
    private CircularsInteractorImpl mCircularsInteractorImpl;

    public CircularsPresenterImpl() {
        mCircularsInteractorImpl = new CircularsInteractorImpl();
    }


    @Override
    public void attachView(CircularsView view) {
        mCircularView = view;
    }

    @Override
    public void detachView() {
        mCircularView = null;

    }

    @Override
    public void getCiculars() {
        mCircularsInteractorImpl.getCirculars(this);
        if(mCircularView != null){
            mCircularView.showHideProgress(true);
        }

    }


    @Override
    public void onCircularsSuccessful(List<CircularsResponse> circularsResponse) {
        if(mCircularView != null){
            mCircularView.showHideProgress(false);
            mCircularView.onCircularsSuccessful(circularsResponse);
        }

    }

    @Override
    public void onCircularsFailed(APIError apiError) {
        if(mCircularView != null){
            mCircularView.showHideProgress(false);
            mCircularView.onCircularsFailed(apiError);
        }

    }

    @Override
    public void onCircularsFailed(Throwable t) {
        if(mCircularView != null){
            mCircularView.showHideProgress(false);
            mCircularView.onCircularsFailed(t);
        }
    }
}
