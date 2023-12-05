package com.sca.in_telligent.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ServiceModule_ProvideCompositeDisposableFactory implements Factory<CompositeDisposable> {
    private final ServiceModule module;

    public ServiceModule_ProvideCompositeDisposableFactory(ServiceModule serviceModule) {
        this.module = serviceModule;
    }

    @Override // javax.inject.Provider
    public CompositeDisposable get() {
        return provideCompositeDisposable(this.module);
    }

    public static ServiceModule_ProvideCompositeDisposableFactory create(ServiceModule serviceModule) {
        return new ServiceModule_ProvideCompositeDisposableFactory(serviceModule);
    }

    public static CompositeDisposable provideCompositeDisposable(ServiceModule serviceModule) {
        return (CompositeDisposable) Preconditions.checkNotNullFromProvides(serviceModule.provideCompositeDisposable());
    }
}
