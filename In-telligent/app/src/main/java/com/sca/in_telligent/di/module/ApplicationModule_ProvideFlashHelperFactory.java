package com.sca.in_telligent.di.module;

import android.content.Context;
import com.sca.in_telligent.openapi.util.FlashHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideFlashHelperFactory implements Factory<FlashHelper> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideFlashHelperFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public FlashHelper get() {
        return provideFlashHelper(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideFlashHelperFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideFlashHelperFactory(applicationModule, provider);
    }

    public static FlashHelper provideFlashHelper(ApplicationModule applicationModule, Context context) {
        return (FlashHelper) Preconditions.checkNotNullFromProvides(applicationModule.provideFlashHelper(context));
    }
}
