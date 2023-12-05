package com.sca.in_telligent.data;

import android.content.Context;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
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
        return new AppDataManager(context, preferencesHelper, apiHelper);
    }
}
