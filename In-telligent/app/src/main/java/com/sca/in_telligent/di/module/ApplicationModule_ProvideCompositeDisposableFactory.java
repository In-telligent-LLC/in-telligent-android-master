package com.sca.in_telligent.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.reactivex.disposables.CompositeDisposable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideCompositeDisposableFactory implements Factory<CompositeDisposable> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideCompositeDisposableFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public CompositeDisposable get() {
        return provideCompositeDisposable(this.module);
    }

    public static ApplicationModule_ProvideCompositeDisposableFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideCompositeDisposableFactory(applicationModule);
    }

    public static CompositeDisposable provideCompositeDisposable(ApplicationModule applicationModule) {
        return (CompositeDisposable) Preconditions.checkNotNullFromProvides(applicationModule.provideCompositeDisposable());
    }
}
