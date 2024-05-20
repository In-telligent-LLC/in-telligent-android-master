package com.sca.in_telligent.di.module;

import com.sca.in_telligent.data.AppDataManager;
import com.sca.in_telligent.data.DataManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideDataManagerFactory implements Factory<DataManager> {
    private final Provider<AppDataManager> appDataManagerProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideDataManagerFactory(ApplicationModule applicationModule, Provider<AppDataManager> provider) {
        this.module = applicationModule;
        this.appDataManagerProvider = provider;
    }

    @Override // javax.inject.Provider
    public DataManager get() {
        return provideDataManager(this.module, this.appDataManagerProvider.get());
    }

    public static ApplicationModule_ProvideDataManagerFactory create(ApplicationModule applicationModule, Provider<AppDataManager> provider) {
        return new ApplicationModule_ProvideDataManagerFactory(applicationModule, provider);
    }

    public static DataManager provideDataManager(ApplicationModule applicationModule, AppDataManager appDataManager) {
        return (DataManager) Preconditions.checkNotNullFromProvides(applicationModule.provideDataManager(appDataManager));
    }
}
