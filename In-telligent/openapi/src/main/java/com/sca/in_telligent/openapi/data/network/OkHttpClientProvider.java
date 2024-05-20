package com.sca.in_telligent.openapi.data.network;

import android.util.Log;

import com.moczul.ok2curl.CurlInterceptor;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.CredentialsProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientProvider {

    public static OkHttpClient getOkHttpClientInterceptor(Interceptor headersInterceptor) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        if (OpenAPI.getInstance().getConfiguration().isDebug()) {
            okClientBuilder.addNetworkInterceptor(new CurlInterceptor(message -> Log.v("Ok2Curl", message)));
        }

        okClientBuilder.addInterceptor(headersInterceptor).
                readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        return okClientBuilder.build();
    }

    public static class PublicInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Headers headers = getHeadersBuilder(request).
                    build();
            request = request.newBuilder().headers(headers).build();
            Response response = chain.proceed(request);
            return response;
        }

        protected Headers.Builder getHeadersBuilder(Request request) {
            return request.headers().newBuilder().
                    add("X-Device-Type", "android").
                    add("User-Agent",
                            "In-telligent Android : " + Integer.toString(OpenAPI.getInstance().getConfiguration().getAppVersion())).
                    add("X-App-Version", Integer.toString(OpenAPI.getInstance().getConfiguration().getAppVersion()));
        }

    }

    public static class AuthInterceptor extends PublicInterceptor {

        private final CredentialsProvider credentialsProvider;

        public AuthInterceptor(CredentialsProvider credentialsProvider) {
            this.credentialsProvider = credentialsProvider;
        }

        @Override
        protected Headers.Builder getHeadersBuilder(Request request) {
            return super.getHeadersBuilder(request).add("Authorization",
                    "Bearer " + credentialsProvider.getAuthToken());
        }
    }
}
