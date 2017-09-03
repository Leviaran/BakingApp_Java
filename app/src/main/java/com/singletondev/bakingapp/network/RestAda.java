package com.singletondev.bakingapp.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Randy Arba on 8/24/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class RestAda {

    static String baseURL = "https://d17h27t6h515a5.cloudfront.net";


    static OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();


    public static Endpoints getRespond(){

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit endpoints;
        endpoints = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okhttpClientBuilder.build())
                .build();

        return endpoints.create(Endpoints.class);


    }
}
