package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.AppLocationUtil;
import com.sca.in_telligent.util.LocationUtil;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideLocationUtilFactory implements Factory<LocationUtil> {
    private final Provider<AppLocationUtil> appLocationUtilProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideLocationUtilFactory(ApplicationModule applicationModule, Provider<AppLocationUtil> provider) {
        this.module = applicationModule;
        this.appLocationUtilProvider = provider;
    }

    @Override // javax.inject.Provider
    public LocationUtil get() {
        return provideLocationUtil(this.module, this.appLocationUtilProvider.get());
    }

    public static ApplicationModule_ProvideLocationUtilFactory create(ApplicationModule applicationModule, Provider<AppLocationUtil> provider) {
        return new ApplicationModule_ProvideLocationUtilFactory(applicationModule, provider);
    }

    public static LocationUtil provideLocationUtil(ApplicationModule applicationModule, AppLocationUtil appLocationUtil) {
        return (LocationUtil) Preconditions.checkNotNullFromProvides(applicationModule.provideLocationUtil(appLocationUtil));
    }
}
