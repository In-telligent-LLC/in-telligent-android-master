package com.in_telligent.openapi;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class IntelligentNetworking {

    private static final String TAG = "IntelligentNetworking";

    private static String hostname;

    public static void initialize(Context ctx) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request originalRequest = chain.request();

                        String authToken = StoredData.getAuthToken(ctx);

                        Request.Builder authedRequest = originalRequest.newBuilder();

                        if (authToken != null && !authToken.equals("")) {
                            authedRequest.addHeader("Authorization", "Bearer " + authToken);
                        }

                        authedRequest.addHeader("X-TimeZone", TimeZone.getDefault().getID());

                        return chain.proceed(authedRequest.build());
                    }
                })
                .build();
        AndroidNetworking.initialize(ctx, okHttpClient);
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC);
    }

    public static String getHostname() {
        return "https://app.in-telligent.com";
    }


    public static ANRequest.GetRequestBuilder get(int port, String path) {
        return get(path, getHostname() + ":" + String.valueOf(port));
    }

    public static ANRequest.GetRequestBuilder get(String path) {
        return get(path, getHostname() + "/api/");
    }

    public static ANRequest.GetRequestBuilder get(String path, String baseUrl) {
        return AndroidNetworking.get(baseUrl + path);
    }


    public static ANRequest.PostRequestBuilder post(int port, String path) {
        return post(path, getHostname() + ":" + String.valueOf(port));
    }

    public static ANRequest.PostRequestBuilder post(String path) {
        return post(path, getHostname() + "/api/");
    }

    public static ANRequest.PostRequestBuilder post(String path, String baseUrl) {
        return AndroidNetworking.post(baseUrl + path);
    }

}
