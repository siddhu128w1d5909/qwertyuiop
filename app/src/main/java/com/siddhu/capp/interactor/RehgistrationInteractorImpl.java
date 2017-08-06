package com.siddhu.capp.interactor;

import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.network.ApiService;
import com.siddhu.capp.network.CollgeRestApi;
import com.siddhu.capp.network.RestClient;
import com.siddhu.capp.utils.ErrorUtils;
import com.siddhu.capp.utils.UrlUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by baji_g on 8/4/2017.
 */

public class RehgistrationInteractorImpl implements RehgistrationInteractor {
    @Override
    public void register(final RegisterRequest registerRequest, final RegistarationListener registarationListener) {
        final Retrofit retrofit = RestClient.getRetrofit();
        final CollgeRestApi apiService = retrofit.create(CollgeRestApi.class);
        final Call<RegisterRequest> call = apiService.doRegister(registerRequest/*, UrlUtility.getCommonRequestHeaders()*/);
        call.enqueue(new Callback<RegisterRequest>() {
            @Override
            public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {

                if(response.isSuccessful()){
                    registarationListener.onRegistarationSuccessful(response.body());
                }else{
                    final APIError apiError = ErrorUtils.parseError(response);
                    registarationListener.onRegistarationFailed(apiError);
                }
            }

            @Override
            public void onFailure(Call<RegisterRequest> call, Throwable t) {
                registarationListener.onRegistarationFailed(t);
            }
        });

    }
}
