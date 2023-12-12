package com.sca.in_telligent.di.module;


import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

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
