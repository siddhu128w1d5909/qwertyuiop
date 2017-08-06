package com.siddhu.capp.network;


import com.siddhu.capp.utils.UrlUtility;

/**
 * Created by baji_g on 5/31/2017.
 */

public class ApiUtils {
   // public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
   // http://192.168.100.8:3080/registers
    public static ApiService getApiService(){
        return RetrofitClient.getClient(UrlUtility.BASE_URL).create(ApiService.class);
    }
}
