package com.sca.in_telligent.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvidePreferenceNameFactory implements Factory<String> {
    private final ApplicationModule module;

    public ApplicationModule_ProvidePreferenceNameFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public String get() {
        return providePreferenceName(this.module);
    }

    public static ApplicationModule_ProvidePreferenceNameFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvidePreferenceNameFactory(applicationModule);
    }

    public static String providePreferenceName(ApplicationModule applicationModule) {
        return (String) Preconditions.checkNotNullFromProvides(applicationModule.providePreferenceName());
    }
}
