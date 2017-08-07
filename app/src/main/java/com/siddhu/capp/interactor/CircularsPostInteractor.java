package com.siddhu.capp.interactor;


import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;

import java.util.List;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface CircularsPostInteractor {

    void postCirculars(CircularsPostListener circularListener);

    interface CircularsPostListener{

        void onCircularsPostSuccessful(CircularsResponse circularsResponse);

        void onCircularsPostFailed(APIError apiError);

        void onCircularsPostFailed(Throwable t);

    }
}
