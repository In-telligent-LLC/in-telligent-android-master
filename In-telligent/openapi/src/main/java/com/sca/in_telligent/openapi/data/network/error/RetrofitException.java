package com.sca.in_telligent.openapi.data.network.error;

import java.io.IOException;
import java.lang.annotation.Annotation;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {
    private final Kind kind;
    private final Response response;
    private final Retrofit retrofit;
    private final String url;

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    public static RetrofitException httpError(String str, Response response, Retrofit retrofit) {
        return new RetrofitException(response.code() + " " + response.message(), str, response, Kind.HTTP, null, retrofit);
    }

    public static RetrofitException networkError(IOException iOException) {
        return new RetrofitException(iOException.getMessage(), null, null, Kind.NETWORK, iOException, null);
    }

    public static RetrofitException unexpectedError(Throwable th) {
        return new RetrofitException(th.getMessage(), null, null, Kind.UNEXPECTED, th, null);
    }

    RetrofitException(String str, String str2, Response response, Kind kind, Throwable th, Retrofit retrofit) {
        super(str, th);
        this.url = str2;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    public String getUrl() {
        return this.url;
    }

    public Response getResponse() {
        return this.response;
    }

    public Kind getKind() {
        return this.kind;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public <T> T getErrorBodyAs(Class<T> cls) throws IOException {
        Response response = this.response;
        if (response == null || response.errorBody() == null) {
            return null;
        }
        return (T) this.retrofit.responseBodyConverter(cls, new Annotation[0]).convert(this.response.errorBody());
    }
}
