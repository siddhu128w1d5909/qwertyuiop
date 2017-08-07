package com.siddhu.capp.interactor;

import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.network.CollgeRestApi;
import com.siddhu.capp.network.RestClient;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.ErrorUtils;
import com.siddhu.capp.utils.PreferenceUtil;
import com.siddhu.capp.utils.UrlUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by baji_g on 8/4/2017.
 */

public class CircularsPostInteractorImpl implements CircularsPostInteractor {

    @Override
    public void postCirculars(final CircularsPostListener circularPostListener) {
        final Retrofit retrofit = RestClient.getRetrofit();
        final CollgeRestApi apiService = retrofit.create(CollgeRestApi.class);
        String mSeesionToken = PreferenceUtil.getInstance(CollegeApplicationClass.getInstance()).getStringParam(AppConstants.SESSION_TOKEN);
        final Call<CircularsResponse> call = apiService.postCircularsList(UrlUtility.getCommonRequestHeaders());
        call.enqueue(new Callback<CircularsResponse>() {
            @Override
            public void onResponse(Call<CircularsResponse> call, Response<CircularsResponse> response) {

                if(response.isSuccessful()){
                    circularPostListener.onCircularsPostSuccessful(response.body());
                }else{
                    final APIError apiError = ErrorUtils.parseError(response);
                    circularPostListener.onCircularsPostFailed(apiError);
                }
            }

            @Override
            public void onFailure(Call<CircularsResponse> call, Throwable t) {
                circularPostListener.onCircularsPostFailed(t);
            }
        });
    }
}
