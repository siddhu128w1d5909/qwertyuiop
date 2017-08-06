package com.siddhu.capp.network;


import com.siddhu.capp.constants.URLConstants;
import com.siddhu.capp.utils.UrlUtility;

import retrofit2.Retrofit;

/**
 * Created by baji_g on 1/6/2017.
 */

public class RestClient {
    private  static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().
                    baseUrl(UrlUtility.BASE_URL).
                    client(OKHttpClient.getInstance()).
                    addConverterFactory(GsonUtils.buildGsonConverter()).
                    build();
        }

        return mRetrofit;
    }


}
