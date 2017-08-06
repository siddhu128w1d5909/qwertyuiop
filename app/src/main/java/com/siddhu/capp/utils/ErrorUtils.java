package com.siddhu.capp.utils;

import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.network.RestClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by dhiman_da on 8/2/2016.
 */
public class ErrorUtils {
    private ErrorUtils() {

    }
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                RestClient.getRetrofit()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            Logger.log(ErrorUtils.class.getName(),e);
            error = new APIError(-1, "Not defined");
        }

        return error;
    }
}
