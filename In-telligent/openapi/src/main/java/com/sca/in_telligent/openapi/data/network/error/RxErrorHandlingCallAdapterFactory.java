package com.sca.in_telligent.openapi.data.network.error;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory mOriginalCallAdapterFactory = RxJava2CallAdapterFactory.create();

    private RxErrorHandlingCallAdapterFactory() {
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, this.mOriginalCallAdapterFactory.get(type, annotationArr, retrofit));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public static class RxCallAdapterWrapper<R> implements CallAdapter<R, Observable<R>> {
        private final Retrofit mRetrofit;
        private final CallAdapter<R, ?> mWrappedCallAdapter;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, ?> callAdapter) {
            this.mRetrofit = retrofit;
            this.mWrappedCallAdapter = callAdapter;
        }

        @Override // retrofit2.CallAdapter
        public Type responseType() {
            return this.mWrappedCallAdapter.responseType();
        }

        @Override // retrofit2.CallAdapter
        public Observable<R> adapt(Call<R> call) {
            return ((Observable) this.mWrappedCallAdapter.adapt(call)).onErrorResumeNext(new Function<Throwable, ObservableSource>() { // from class: com.sca.in_telligent.openapi.data.network.error.RxErrorHandlingCallAdapterFactory.RxCallAdapterWrapper.1
                @Override // io.reactivex.functions.Function
                public Observable apply(Throwable th) {
                    return Observable.error(RxCallAdapterWrapper.this.asRetrofitException(th));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public RetrofitException asRetrofitException(Throwable th) {
            if (th instanceof HttpException) {
                Response<?> response = ((HttpException) th).response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, this.mRetrofit);
            } else if (th instanceof IOException) {
                return RetrofitException.networkError((IOException) th);
            } else {
                return RetrofitException.unexpectedError(th);
            }
        }
    }
}
