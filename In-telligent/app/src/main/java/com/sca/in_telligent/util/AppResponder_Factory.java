package com.sca.in_telligent.util;

import com.sca.in_telligent.data.DataManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppResponder_Factory implements Factory<AppResponder> {
    private final Provider<DataManager> dataManagerProvider;

    public AppResponder_Factory(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    @Override // javax.inject.Provider
    public AppResponder get() {
        AppResponder newInstance = newInstance();
        AppResponder_MembersInjector.injectDataManager(newInstance, this.dataManagerProvider.get());
        return newInstance;
    }

    public static AppResponder_Factory create(Provider<DataManager> provider) {
        return new AppResponder_Factory(provider);
    }

    public static AppResponder newInstance() {
        return new AppResponder();
    }
}
