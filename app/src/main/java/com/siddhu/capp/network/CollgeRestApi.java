package com.siddhu.capp.network;



import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.login.LoginRequest;
import com.siddhu.capp.models.login.LoginResponse;
import com.siddhu.capp.utils.PreferenceUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface CollgeRestApi {
    /**
     * Login Api
     **/
    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterRequest> doRegister(@Body RegisterRequest registerRequest);
     @GET("circular")
    Call<List<CircularsResponse>> getCircularsList(@HeaderMap Map<String, String> headerMap);
   /* @POST("register")
    Call<RegisterRequest> register(@Body RegisterRequest registerRequest,
                                   @HeaderMap Map<String, String> headerMap);*/
}
