package com.sca.in_telligent.di.module;

import android.content.Context;
import android.os.Vibrator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideVibratorFactory implements Factory<Vibrator> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideVibratorFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public Vibrator get() {
        return provideVibrator(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideVibratorFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideVibratorFactory(applicationModule, provider);
    }

    public static Vibrator provideVibrator(ApplicationModule applicationModule, Context context) {
        return (Vibrator) Preconditions.checkNotNullFromProvides(applicationModule.provideVibrator(context));
    }
}
