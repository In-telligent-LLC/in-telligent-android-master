package com.sca.in_telligent.openapi.data.network;

import android.util.Log;

import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.CredentialsProvider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpClientProvider {
    public static OkHttpClient getOkHttpClientInterceptor(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (OpenAPI.getInstance().getConfiguration().isDebug()) {
            builder.addNetworkInterceptor(new CurlInterceptor(new Loggable() { // from class: com.sca.in_telligent.openapi.data.network.OkHttpClientProvider$$ExternalSyntheticLambda0
                @Override // com.moczul.ok2curl.logger.Loggable
                public final void log(String str) {
                    Log.v("Ok2Curl", str);
                }
            }));
        }
        builder.addInterceptor(interceptor).readTimeout(60L, TimeUnit.SECONDS).connectTimeout(60L, TimeUnit.SECONDS);
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        return builder.build();
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static class PublicInterceptor implements Interceptor {
        @Override // okhttp3.Interceptor
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request.newBuilder().headers(getHeadersBuilder(request).build()).build());
        }

        protected Headers.Builder getHeadersBuilder(Request request) {
            return request.headers().newBuilder().add("X-Device-Type", "android").add("User-Agent", "In-telligent Android : " + Integer.toString(OpenAPI.getInstance().getConfiguration().getAppVersion())).add("X-App-Version", Integer.toString(OpenAPI.getInstance().getConfiguration().getAppVersion()));
        }
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static class AuthInterceptor extends PublicInterceptor {
        private final CredentialsProvider credentialsProvider;

        public AuthInterceptor(CredentialsProvider credentialsProvider) {
            this.credentialsProvider = credentialsProvider;
        }

        @Override // com.sca.in_telligent.openapi.data.network.OkHttpClientProvider.PublicInterceptor
        protected Headers.Builder getHeadersBuilder(Request request) {
            return super.getHeadersBuilder(request).add("Authorization", "Bearer " + this.credentialsProvider.getAuthToken());
        }
    }
}
