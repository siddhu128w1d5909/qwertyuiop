package com.siddhu.capp.network;

import com.siddhu.capp.models.RegisterRequest;
import com.siddhu.capp.models.ServerRequest;
import com.siddhu.capp.models.ServerResponse;
import com.siddhu.capp.ui.circular.Comments;
import com.siddhu.capp.utils.UrlUtility;
import com.siddhu.capp.utils.Utility;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by baji_g on 5/31/2017.
 */

public interface ApiService {

    @GET("/comments")
    Call<List<Comments>> getCommets();
    @POST("login/")
    Call<ServerResponse> operation(@Body ServerRequest request);

    @POST("/registers")
    Call<RegisterRequest> register(@Body RegisterRequest registerRequest,
                                   @HeaderMap Map<String, String> headerMap);

}
