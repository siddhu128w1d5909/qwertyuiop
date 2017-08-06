package com.siddhu.capp.interactor;

import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.error.APIError;

/**
 * Created by baji_g on 8/4/2017.
 */

public interface RehgistrationInteractor {
     void register(RegisterRequest registerRequest,RegistarationListener registarationListener);
     interface  RegistarationListener{
         void onRegistarationSuccessful(RegisterRequest registerRequest);

         void onRegistarationFailed(APIError apiError);

         void onRegistarationFailed(Throwable t);
     }
}
