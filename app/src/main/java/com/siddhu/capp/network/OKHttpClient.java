package com.siddhu.capp.network;

import android.support.design.BuildConfig;
import android.util.Log;

import com.siddhu.capp.app.CollegeApplicationClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by baji_g on 1/6/2017.
 */

public class OKHttpClient {
    public OKHttpClient() {
    }
    public static OkHttpClient getInstance() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                HttpLoggingInterceptor.Level.NONE);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        long CONNECTION_TIMEOUT = 30000;
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                // try the request
                Response response = chain.proceed(request);

                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {

                    Log.d("intercept", "Request is not successful - " + tryCount);

                    tryCount++;

                    // retry the request
                    response = chain.proceed(request);
                }

                // otherwise just pass the original response on
                return response;
            }
        });
        builder.interceptors().add(logging);
        //builder.cache(new Cache(.getCacheDir(), 20 * 1024 * 1024));

        OkHttpClient sClient = builder.build();

        return sClient;
    }
}
