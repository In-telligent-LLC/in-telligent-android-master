package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ServiceModule_ProvideSchedulerProviderFactory implements Factory<SchedulerProvider> {
    private final ServiceModule module;

    public ServiceModule_ProvideSchedulerProviderFactory(ServiceModule serviceModule) {
        this.module = serviceModule;
    }

    @Override // javax.inject.Provider
    public SchedulerProvider get() {
        return provideSchedulerProvider(this.module);
    }

    public static ServiceModule_ProvideSchedulerProviderFactory create(ServiceModule serviceModule) {
        return new ServiceModule_ProvideSchedulerProviderFactory(serviceModule);
    }

    public static SchedulerProvider provideSchedulerProvider(ServiceModule serviceModule) {
        return (SchedulerProvider) Preconditions.checkNotNullFromProvides(serviceModule.provideSchedulerProvider());
    }
}
