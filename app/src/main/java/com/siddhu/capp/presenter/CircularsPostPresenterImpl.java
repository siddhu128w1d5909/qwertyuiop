package com.siddhu.capp.presenter;
import com.siddhu.capp.interactor.CircularsInteractor;
import com.siddhu.capp.interactor.CircularsInteractorImpl;
import com.siddhu.capp.interactor.CircularsPostInteractor;
import com.siddhu.capp.interactor.CircularsPostInteractorImpl;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public class CircularsPostPresenterImpl implements CircularsPostPresenter,CircularsPostInteractor.CircularsPostListener {

    private CircularsPostPresenter.CircularsPostView mCircularPostView;
    private CircularsPostInteractorImpl mCircularsPostInteractorImpl;

    public CircularsPostPresenterImpl() {
        mCircularsPostInteractorImpl = new CircularsPostInteractorImpl();
    }


    @Override
    public void attachView(CircularsPostPresenter.CircularsPostView view) {
        mCircularPostView = view;
    }

    @Override
    public void detachView() {
        mCircularPostView = null;

    }

    @Override
    public void postCiculars(CircularsResponse circularsResponse) {
        mCircularsPostInteractorImpl.postCirculars(this);
        if(mCircularPostView != null){
            mCircularPostView.showHideProgress(true);
        }
    }


    @Override
    public void onCircularsPostSuccessful(CircularsResponse circularsResponse) {
        if(mCircularPostView != null){
            mCircularPostView.showHideProgress(false);
            mCircularPostView.onCircularsPostSuccessful(circularsResponse);
        }
    }

    @Override
    public void onCircularsPostFailed(APIError apiError) {
        if(mCircularPostView != null){
            mCircularPostView.showHideProgress(false);
            mCircularPostView.onCircularsPostFailed(apiError);
        }
    }

    @Override
    public void onCircularsPostFailed(Throwable t) {
        if(mCircularPostView != null){
            mCircularPostView.showHideProgress(false);
            mCircularPostView.onCircularsPostFailed(t);
        }
    }
}
