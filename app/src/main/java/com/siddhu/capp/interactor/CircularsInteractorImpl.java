package com.siddhu.capp.interactor;

import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.models.RegisterRequest;
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

public class CircularsInteractorImpl implements CircularsInteractor {
    @Override
    public void getCirculars(final CircularsListener circularsListener) {
        final Retrofit retrofit = RestClient.getRetrofit();
        final CollgeRestApi apiService = retrofit.create(CollgeRestApi.class);
        String mSeesionToken = PreferenceUtil.getInstance(CollegeApplicationClass.getInstance()).getStringParam(AppConstants.SESSION_TOKEN);
        final Call<List<CircularsResponse>> call = apiService.getCircularsList(UrlUtility.getCommonRequestHeaders());
        call.enqueue(new Callback<List<CircularsResponse>>() {
            @Override
            public void onResponse(Call<List<CircularsResponse>> call, Response<List<CircularsResponse>> response) {

                if(response.isSuccessful()){
                    circularsListener.onCircularsSuccessful(response.body());
                }else{
                    final APIError apiError = ErrorUtils.parseError(response);
                    circularsListener.onCircularsFailed(apiError);
                }
            }

            @Override
            public void onFailure(Call<List<CircularsResponse>> call, Throwable t) {
                circularsListener.onCircularsFailed(t);
            }
        });

       /* @Override
        public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
            for(int i = 0; i < response.body().size(); i++){
                Comments comments = response.body().get(i);
                mCommnetsList.add(comments);
            }
            commentsAdapter = new CommentsAdapter(mCommnetsList,CommentsActivity.this);
            mCommentsRecycler.setAdapter(commentsAdapter);
        }*/

    }

}
