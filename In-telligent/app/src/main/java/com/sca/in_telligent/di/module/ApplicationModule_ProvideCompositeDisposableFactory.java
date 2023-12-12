package com.sca.in_telligent.di.module;

import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public final class ApplicationModule_ProvideCompositeDisposableFactory implements Factory<CompositeDisposable> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideCompositeDisposableFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override
    public CompositeDisposable get() {
        return provideCompositeDisposable(this.module);
    }

    public static ApplicationModule_ProvideCompositeDisposableFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideCompositeDisposableFactory(applicationModule);
    }

    public static CompositeDisposable provideCompositeDisposable(ApplicationModule applicationModule) {
        return applicationModule.provideCompositeDisposable();
    }
}
