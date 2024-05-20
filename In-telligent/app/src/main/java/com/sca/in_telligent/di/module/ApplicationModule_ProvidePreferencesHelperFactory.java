package com.sca.in_telligent.di.module;

import com.sca.in_telligent.data.prefs.AppPreferencesHelper;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvidePreferencesHelperFactory implements Factory<PreferencesHelper> {
    private final Provider<AppPreferencesHelper> appPreferencesHelperProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvidePreferencesHelperFactory(ApplicationModule applicationModule, Provider<AppPreferencesHelper> provider) {
        this.module = applicationModule;
        this.appPreferencesHelperProvider = provider;
    }

    @Override // javax.inject.Provider
    public PreferencesHelper get() {
        return providePreferencesHelper(this.module, this.appPreferencesHelperProvider.get());
    }

    public static ApplicationModule_ProvidePreferencesHelperFactory create(ApplicationModule applicationModule, Provider<AppPreferencesHelper> provider) {
        return new ApplicationModule_ProvidePreferencesHelperFactory(applicationModule, provider);
    }

    public static PreferencesHelper providePreferencesHelper(ApplicationModule applicationModule, AppPreferencesHelper appPreferencesHelper) {
        return (PreferencesHelper) Preconditions.checkNotNullFromProvides(applicationModule.providePreferencesHelper(appPreferencesHelper));
    }
}
