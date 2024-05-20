package com.sca.in_telligent.openapi.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sca.in_telligent.openapi.data.network.ApiEndPoint;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.OkHttpClientProvider;
import com.sca.in_telligent.openapi.data.network.error.RxErrorHandlingCallAdapterFactory;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.Objects.requireNonNull;

/**
 * Created by Marcos Ambrosi on 2/6/19.
 */
public class ApiHelperProvider {

    private static ApiHelper uploadApiHelper;
    private static ApiHelper publicApiHelper;
    private static ApiHelper authApiHelper;

    public static void initialize(Context context) {

        requireNonNull(context, "Context cannot be null");
        OkHttpClientProvider.PublicInterceptor publicInterceptor = new OkHttpClientProvider.PublicInterceptor();
        OkHttpClient publicOkHttpClient = OkHttpClientProvider.getOkHttpClientInterceptor(publicInterceptor);
        publicApiHelper = getApiHelper(ApiEndPoint.BASE_URL, publicOkHttpClient);

        OpenApiPreferencesHelper openApiPreferencesHelper = new OpenApiPreferencesHelperImpl(context, OpenApiPreferencesHelperImpl.PREF_NAME);

        OkHttpClientProvider.AuthInterceptor authInterceptor = new OkHttpClientProvider.AuthInterceptor(() -> openApiPreferencesHelper.getAccessToken());
        OkHttpClient authOkHttpClient = OkHttpClientProvider.getOkHttpClientInterceptor(authInterceptor);
        uploadApiHelper = getApiHelper(ApiEndPoint.BASE_UPLOAD, authOkHttpClient);
        authApiHelper = getApiHelper(ApiEndPoint.BASE_URL, authOkHttpClient);
    }

    private static ApiHelper getApiHelper(@NonNull String baseUrl, @NonNull OkHttpClient okHttpClient) {
        requireNonNull(baseUrl, "Base URL cannot be null");
        requireNonNull(okHttpClient, "OkHttpClient cannot be null");
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient).build();
        return retrofit.create(ApiHelper.class);
    }


    public static ApiHelper getPublicApiHelper() {
        if (publicApiHelper == null) {
            throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
        }

        return publicApiHelper;
    }

    public static ApiHelper getAuthApiHelper() {
        if (authApiHelper == null) {
            throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
        }

        return authApiHelper;
    }


    public static ApiHelper getUploadApiHelper() {
        if (uploadApiHelper == null) {
            throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
        }

        return uploadApiHelper;
    }


}
