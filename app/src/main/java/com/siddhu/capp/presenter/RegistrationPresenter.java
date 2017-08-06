package com.siddhu.capp.presenter;

import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.error.APIError;

/**
 * Created by baji_g on 8/4/2017.
 */

public interface RegistrationPresenter {
    void attachView(RegistrationView view);

    void detachView();

    void register(RegisterRequest registerRequest);

    interface RegistrationView{
        void onRegistrationSuccessful(RegisterRequest response);

        void onRegistrationFailed(APIError apiError);

        void onRegistrationFailed(Throwable throwable);

        void showHideProgress(boolean show);
    }


}
