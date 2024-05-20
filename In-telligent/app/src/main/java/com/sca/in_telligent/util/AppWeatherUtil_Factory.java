package com.sca.in_telligent.util;

import android.content.Context;
import com.sca.in_telligent.data.DataManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppWeatherUtil_Factory implements Factory<AppWeatherUtil> {
    private final Provider<Context> contextProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<LocationUtil> locationUtilProvider;

    public AppWeatherUtil_Factory(Provider<Context> provider, Provider<LocationUtil> provider2, Provider<DataManager> provider3) {
        this.contextProvider = provider;
        this.locationUtilProvider = provider2;
        this.dataManagerProvider = provider3;
    }

    @Override // javax.inject.Provider
    public AppWeatherUtil get() {
        AppWeatherUtil newInstance = newInstance(this.contextProvider.get(), this.locationUtilProvider.get());
        AppWeatherUtil_MembersInjector.injectDataManager(newInstance, this.dataManagerProvider.get());
        return newInstance;
    }

    public static AppWeatherUtil_Factory create(Provider<Context> provider, Provider<LocationUtil> provider2, Provider<DataManager> provider3) {
        return new AppWeatherUtil_Factory(provider, provider2, provider3);
    }

    public static AppWeatherUtil newInstance(Context context, LocationUtil locationUtil) {
        return new AppWeatherUtil(context, locationUtil);
    }
}
