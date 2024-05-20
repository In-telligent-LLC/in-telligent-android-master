package com.sca.in_telligent.util;

import com.sca.in_telligent.data.DataManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppWeatherUtil_MembersInjector implements MembersInjector<AppWeatherUtil> {
    private final Provider<DataManager> dataManagerProvider;

    public AppWeatherUtil_MembersInjector(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    public static MembersInjector<AppWeatherUtil> create(Provider<DataManager> provider) {
        return new AppWeatherUtil_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(AppWeatherUtil appWeatherUtil) {
        injectDataManager(appWeatherUtil, this.dataManagerProvider.get());
    }

    public static void injectDataManager(AppWeatherUtil appWeatherUtil, DataManager dataManager) {
        appWeatherUtil.dataManager = dataManager;
    }
}
