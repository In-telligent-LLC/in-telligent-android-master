package com.sca.in_telligent.openapi.data;

import static java.util.Objects.requireNonNull;

import android.content.Context;

import com.openapi.BuildConfig;
import com.sca.in_telligent.openapi.data.network.ApiEndPoint;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.OkHttpClientProvider;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelperProvider {
    private static boolean isMocked = true;
    private static ApiHelper authApiHelper;
    private static ApiHelper publicApiHelper;
    private static ApiHelper uploadApiHelper;

    public static void setMocked(boolean mocked) {
        isMocked = mocked;
    }

    public static void initialize(Context context) {

        requireNonNull(context, "Context cannot be null");
        OkHttpClientProvider.PublicInterceptor publicInterceptor = new OkHttpClientProvider.PublicInterceptor();
        OkHttpClient publicOkHttpClient = OkHttpClientProvider.getOkHttpClientInterceptor(publicInterceptor);
        publicApiHelper = getApiHelper(BuildConfig.API_URL, publicOkHttpClient);

        OpenApiPreferencesHelper openApiPreferencesHelper = new OpenApiPreferencesHelperImpl(context, OpenApiPreferencesHelperImpl.PREF_NAME);

        OkHttpClientProvider.AuthInterceptor authInterceptor = new OkHttpClientProvider.AuthInterceptor(() -> openApiPreferencesHelper.getAccessToken());
        OkHttpClient authOkHttpClient = OkHttpClientProvider.getOkHttpClientInterceptor(authInterceptor);
        uploadApiHelper = getApiHelper(ApiEndPoint.BASE_UPLOAD, authOkHttpClient);
        authApiHelper = getApiHelper(BuildConfig.API_URL, authOkHttpClient);
    }


    private static ApiHelper getApiHelper(@NonNull String baseUrl, @NonNull OkHttpClient okHttpClient) {
        requireNonNull(baseUrl, "Base URL cannot be null");
        requireNonNull(okHttpClient, "OkHttpClient cannot be null");
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(
                        RxJava3CallAdapterFactory.create()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)

                .client(okHttpClient).build();
        return retrofit.create(ApiHelper.class);
    }

    public static ApiHelper getPublicApiHelper() {
        if (isMocked) {
            return null;
        }
        ApiHelper apiHelper = publicApiHelper;
        if (apiHelper != null) {
            return apiHelper;
        }
        throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
    }

    public static ApiHelper getAuthApiHelper() {
        if (isMocked) {
            return null;
        }
        ApiHelper apiHelper = authApiHelper;
        if (apiHelper != null) {
            return apiHelper;
        }
        throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
    }

    public static ApiHelper getUploadApiHelper() {
        if (isMocked) {
            return null;
        }
        ApiHelper apiHelper = uploadApiHelper;
        if (apiHelper != null) {
            return apiHelper;
        }
        throw new UnsupportedOperationException("You must call ApiHelperProvider.init(context) first");
    }
}
