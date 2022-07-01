package com.yobitelecom.demoapp.method;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestClient {

    private static OkHttpClient okHttpClient;


    public static void post(String url, String body, Callback callback){
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Request request=new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body))
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
