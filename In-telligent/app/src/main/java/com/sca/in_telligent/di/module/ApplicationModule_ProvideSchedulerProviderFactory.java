package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideSchedulerProviderFactory implements Factory<SchedulerProvider> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideSchedulerProviderFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public SchedulerProvider get() {
        return provideSchedulerProvider(this.module);
    }

    public static ApplicationModule_ProvideSchedulerProviderFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideSchedulerProviderFactory(applicationModule);
    }

    public static SchedulerProvider provideSchedulerProvider(ApplicationModule applicationModule) {
        return (SchedulerProvider) Preconditions.checkNotNullFromProvides(applicationModule.provideSchedulerProvider());
    }
}
