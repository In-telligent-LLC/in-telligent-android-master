package com.sca.in_telligent.util;

import com.sca.in_telligent.data.DataManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppResponder_MembersInjector implements MembersInjector<AppResponder> {
    private final Provider<DataManager> dataManagerProvider;

    public AppResponder_MembersInjector(Provider<DataManager> provider) {
        this.dataManagerProvider = provider;
    }

    public static MembersInjector<AppResponder> create(Provider<DataManager> provider) {
        return new AppResponder_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(AppResponder appResponder) {
        injectDataManager(appResponder, this.dataManagerProvider.get());
    }

    public static void injectDataManager(AppResponder appResponder, DataManager dataManager) {
        appResponder.dataManager = dataManager;
    }
}
