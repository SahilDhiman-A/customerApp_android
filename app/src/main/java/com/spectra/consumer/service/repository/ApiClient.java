package com.spectra.consumer.service.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.spectra.consumer.BuildConfig;
import java.util.concurrent.TimeUnit;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitAutoPAy = null;
    private static Retrofit internetNotWorkingClient = null;
    private static Retrofit fdssClient = null;
    private static Retrofit srClient = null;
    static Retrofit getClient() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 2*60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(2, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                }
            }
        }
        return retrofit;
    }
    static Retrofit getAutoPayClient() {
        if (retrofitAutoPAy == null) {
            synchronized (Retrofit.class) {
                if (retrofitAutoPAy == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    retrofitAutoPAy = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL_AUTO_PAY)
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                }
            }
        }
        return retrofitAutoPAy;
    }
    static Retrofit getInternetNotWorkingClient() {
        if (internetNotWorkingClient == null) {
            synchronized (Retrofit.class) {
                if (internetNotWorkingClient == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    internetNotWorkingClient = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL_2+"api/v2/")
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                }
            }
        }
        return internetNotWorkingClient;
    }


    static Retrofit getFDSSClient() {
        if (fdssClient == null) {
            synchronized (Retrofit.class) {
                if (fdssClient == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    fdssClient = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL_2+"api/v1/")
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                }
            }
        }
        return fdssClient;
    }


    static Retrofit getSRClient() {
        if (srClient == null) {
            synchronized (Retrofit.class) {
                if (srClient == null) {
                    Gson gson = new GsonBuilder().create();
                    RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    int REQUEST_TIMEOUT = 60;
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS).build();
                    srClient = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL_2)
                            .client(okHttpClient)
                            .addCallAdapterFactory(rxAdapter)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                }
            }
        }
        return srClient;
    }

}