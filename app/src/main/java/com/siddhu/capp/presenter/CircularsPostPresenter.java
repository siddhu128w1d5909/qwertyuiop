package com.siddhu.capp.presenter;


import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface CircularsPostPresenter {

    void  attachView(CircularsPostView view);

    void detachView();

    void postCiculars(CircularsResponse circularsResponse);

    interface CircularsPostView{
        void onCircularsPostSuccessful(CircularsResponse response);

        void onCircularsPostFailed(APIError apiError);

        void onCircularsPostFailed(Throwable throwable);

        void showHideProgress(boolean show);
    }
}
