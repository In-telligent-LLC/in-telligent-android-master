package com.sca.in_telligent.data;

import android.content.Context;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;

import java.util.List;

import dagger.internal.Factory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Provider;

public final class AppDataManager_Factory implements Factory<AppDataManager> {
    private final Provider<ApiHelper> apiHelperProvider;
    private final Provider<Context> contextProvider;
    private final Provider<PreferencesHelper> preferencesHelperProvider;

    public AppDataManager_Factory(Provider<Context> provider, Provider<PreferencesHelper> provider2, Provider<ApiHelper> provider3) {
        this.contextProvider = provider;
        this.preferencesHelperProvider = provider2;
        this.apiHelperProvider = provider3;
    }

    @Override // javax.inject.Provider
    public AppDataManager get() {
        return newInstance(this.contextProvider.get(), this.preferencesHelperProvider.get(), this.apiHelperProvider.get());
    }

    public static AppDataManager_Factory create(Provider<Context> provider, Provider<PreferencesHelper> provider2, Provider<ApiHelper> provider3) {
        return new AppDataManager_Factory(provider, provider2, provider3);
    }

    public static AppDataManager newInstance(Context context, PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        return new AppDataManager(context, preferencesHelper, apiHelper) {
            @Override
            public Observable<SuccessResponse> createNotification(List<MultipartBody.Part> list, RequestBody requestBody, RequestBody requestBody2, RequestBody requestBody3, RequestBody requestBody4, RequestBody requestBody5, RequestBody requestBody6) {
                return null;
            }

            @Override
            public Observable<SuccessResponse> suggestNotification(List<MultipartBody.Part> list, RequestBody requestBody, RequestBody requestBody2, RequestBody requestBody3) {
                return null;
            }
        };
    }
}
