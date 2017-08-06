package com.siddhu.capp.interactor;


import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.models.login.LoginResponse;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface CircularsInteractor {

    void getCirculars(CircularsListener circularListener);

    interface CircularsListener{

        void onCircularsSuccessful(List<CircularsResponse> circularsResponse);

        void onCircularsFailed(APIError apiError);

        void onCircularsFailed(Throwable t);

    }
}
