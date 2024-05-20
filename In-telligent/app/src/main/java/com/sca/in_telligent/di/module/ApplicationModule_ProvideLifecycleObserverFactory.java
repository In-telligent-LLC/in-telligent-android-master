package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.AppLifecycleObserver;
import com.sca.in_telligent.util.LifecycleInterface;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideLifecycleObserverFactory implements Factory<LifecycleInterface> {
    private final Provider<AppLifecycleObserver> appLifecycleObserverProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideLifecycleObserverFactory(ApplicationModule applicationModule, Provider<AppLifecycleObserver> provider) {
        this.module = applicationModule;
        this.appLifecycleObserverProvider = provider;
    }

    @Override // javax.inject.Provider
    public LifecycleInterface get() {
        return provideLifecycleObserver(this.module, this.appLifecycleObserverProvider.get());
    }

    public static ApplicationModule_ProvideLifecycleObserverFactory create(ApplicationModule applicationModule, Provider<AppLifecycleObserver> provider) {
        return new ApplicationModule_ProvideLifecycleObserverFactory(applicationModule, provider);
    }

    public static LifecycleInterface provideLifecycleObserver(ApplicationModule applicationModule, AppLifecycleObserver appLifecycleObserver) {
        return (LifecycleInterface) Preconditions.checkNotNullFromProvides(applicationModule.provideLifecycleObserver(appLifecycleObserver));
    }
}
