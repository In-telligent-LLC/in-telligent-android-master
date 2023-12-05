package com.sca.in_telligent.di.module;

import com.sca.in_telligent.openapi.data.network.ApiHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideApiHelperFactory implements Factory<ApiHelper> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideApiHelperFactory(ApplicationModule applicationModule) {
        this.module = applicationModule;
    }

    @Override // javax.inject.Provider
    public ApiHelper get() {
        return provideApiHelper(this.module);
    }

    public static ApplicationModule_ProvideApiHelperFactory create(ApplicationModule applicationModule) {
        return new ApplicationModule_ProvideApiHelperFactory(applicationModule);
    }

    public static ApiHelper provideApiHelper(ApplicationModule applicationModule) {
        return (ApiHelper) Preconditions.checkNotNullFromProvides(applicationModule.provideApiHelper());
    }
}
