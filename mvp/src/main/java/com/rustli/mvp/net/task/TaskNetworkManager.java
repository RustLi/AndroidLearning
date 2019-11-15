package com.rustli.mvp.net.task;

import com.rustli.mvp.BuildConfig;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskNetworkManager {

    private static final String BASE_URL = "http://192.168.8.28";

    private static final OkHttpClient OK_CLIENT =  new OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)
            )
            .build();

    private static Retrofit retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OK_CLIENT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static TaskRequest taskRequest = retrofitBuilder.create(TaskRequest.class);

    //Url不固定
    public static TaskRequest getTaskRequest(){
        return taskRequest;
    }

    //Url固定
//    public static TaskRequest getTaskRequest(){
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(OK_CLIENT)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(TaskRequest.class);
//    }

    //更新url，需要重新生成Retrofit对象
    public static void updateUrl(String url){
        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .client(OK_CLIENT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskRequest = retrofitBuilder.create(TaskRequest.class);
    }
}
