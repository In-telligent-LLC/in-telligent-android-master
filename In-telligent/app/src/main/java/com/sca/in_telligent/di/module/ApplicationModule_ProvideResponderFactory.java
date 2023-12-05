package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.AppResponder;
import com.sca.in_telligent.util.Responder;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideResponderFactory implements Factory<Responder> {
    private final Provider<AppResponder> appResponderProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideResponderFactory(ApplicationModule applicationModule, Provider<AppResponder> provider) {
        this.module = applicationModule;
        this.appResponderProvider = provider;
    }

    @Override // javax.inject.Provider
    public Responder get() {
        return provideResponder(this.module, this.appResponderProvider.get());
    }

    public static ApplicationModule_ProvideResponderFactory create(ApplicationModule applicationModule, Provider<AppResponder> provider) {
        return new ApplicationModule_ProvideResponderFactory(applicationModule, provider);
    }

    public static Responder provideResponder(ApplicationModule applicationModule, AppResponder appResponder) {
        return (Responder) Preconditions.checkNotNullFromProvides(applicationModule.provideResponder(appResponder));
    }
}
