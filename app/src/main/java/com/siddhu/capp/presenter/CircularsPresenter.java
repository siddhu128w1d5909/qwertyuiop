package com.siddhu.capp.presenter;


import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface CircularsPresenter {

    void  attachView(CircularsView view);

    void detachView();

    void getCiculars();

    interface CircularsView{
        void onCircularsSuccessful(List<CircularsResponse> response);

        void onCircularsFailed(APIError apiError);

        void onCircularsFailed(Throwable throwable);

        void showHideProgress(boolean show);
    }
}
