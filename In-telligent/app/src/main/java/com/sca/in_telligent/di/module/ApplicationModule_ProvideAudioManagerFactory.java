package com.sca.in_telligent.di.module;

import android.content.Context;
import android.media.AudioManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideAudioManagerFactory implements Factory<AudioManager> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideAudioManagerFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public AudioManager get() {
        return provideAudioManager(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideAudioManagerFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideAudioManagerFactory(applicationModule, provider);
    }

    public static AudioManager provideAudioManager(ApplicationModule applicationModule, Context context) {
        return (AudioManager) Preconditions.checkNotNullFromProvides(applicationModule.provideAudioManager(context));
    }
}
