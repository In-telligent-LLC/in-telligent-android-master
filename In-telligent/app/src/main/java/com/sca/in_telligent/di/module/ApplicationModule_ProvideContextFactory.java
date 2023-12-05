package com.sca.in_telligent.di.module;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideContextFactory implements Factory<Context> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideContextFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideContext(this.module);
    }

    public static ApplicationModule_ProvideContextFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideContextFactory(applicationModule);
    }

    public static Context provideContext(ApplicationModule applicationModule) {
        return (Context) Preconditions.checkNotNullFromProvides(applicationModule.provideContext());
    }
}
