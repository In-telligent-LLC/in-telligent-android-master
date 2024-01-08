package com.sca.in_telligent.openapi.data;

import android.content.Context;
import com.sca.in_telligent.openapi.data.network.ApiEndPoint;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.OkHttpClientProvider;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;
import java.util.Objects;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
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
        Objects.requireNonNull(context, "Context cannot be null");
        if (!isMocked) {
            publicApiHelper = getApiHelper(ApiEndPoint.BASE_URL, OkHttpClientProvider.getOkHttpClientInterceptor(new OkHttpClientProvider.PublicInterceptor()));
            final OpenApiPreferencesHelperImpl openApiPreferencesHelperImpl = new OpenApiPreferencesHelperImpl(context, OpenApiPreferencesHelperImpl.PREF_NAME);
            OkHttpClient okHttpClientInterceptor = OkHttpClientProvider.getOkHttpClientInterceptor(new OkHttpClientProvider.AuthInterceptor(new CredentialsProvider() { // from class: com.sca.in_telligent.openapi.data.ApiHelperProvider$$ExternalSyntheticLambda0
                @Override // com.sca.in_telligent.openapi.data.CredentialsProvider
                public String getAuthToken() {
                    String accessToken;
                    accessToken = getAuthToken();
                    return accessToken;
                }
            }));
            uploadApiHelper = getApiHelper(ApiEndPoint.BASE_UPLOAD, okHttpClientInterceptor);
            authApiHelper = getApiHelper(ApiEndPoint.BASE_URL, okHttpClientInterceptor);
        }
    }

    private static ApiHelper getApiHelper(String str, OkHttpClient okHttpClient) {
        Objects.requireNonNull(str, "Base URL cannot be null");
        Objects.requireNonNull(okHttpClient, "OkHttpClient cannot be null");
        if (!isMocked) {
            return (ApiHelper) new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(str)
                    .client(okHttpClient)
                    .build()
                    .create(ApiHelper.class);

        }
        return null;
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
