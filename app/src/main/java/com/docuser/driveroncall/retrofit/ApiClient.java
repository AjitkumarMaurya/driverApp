package com.docuser.driveroncall.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2/26/2018.
 */
public class ApiClient {
    private static final String ROOT_URL = "http://www.driveroncall.co.in/";
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApiInterface getApiService() {
        return getRetrofitInstance().create(ApiInterface.class);
    }


}
