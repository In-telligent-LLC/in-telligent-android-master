package com.sca.in_telligent.di.module;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideApplicationFactory implements Factory<Application> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideApplicationFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public Application get() {
        return provideApplication(this.module);
    }

    public static ApplicationModule_ProvideApplicationFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideApplicationFactory(applicationModule);
    }

    public static Application provideApplication(ApplicationModule applicationModule) {
        return (Application) Preconditions.checkNotNullFromProvides(applicationModule.provideApplication());
    }
}
