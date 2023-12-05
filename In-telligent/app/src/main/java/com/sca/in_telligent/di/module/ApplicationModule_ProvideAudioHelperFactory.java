package com.sca.in_telligent.di.module;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.openapi.util.FlashHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideAudioHelperFactory implements Factory<AudioHelper> {
    private final Provider<AudioManager> audioManagerProvider;
    private final Provider<Context> contextProvider;
    private final Provider<FlashHelper> flashHelperProvider;
    private final ApplicationModule module;
    private final Provider<Vibrator> vibratorProvider;

    public ApplicationModule_ProvideAudioHelperFactory(ApplicationModule applicationModule, Provider<Context> provider, Provider<AudioManager> provider2, Provider<Vibrator> provider3, Provider<FlashHelper> provider4) {
        this.module = applicationModule;
        this.contextProvider = provider;
        this.audioManagerProvider = provider2;
        this.vibratorProvider = provider3;
        this.flashHelperProvider = provider4;
    }

    @Override // javax.inject.Provider
    public AudioHelper get() {
        return provideAudioHelper(this.module, this.contextProvider.get(), this.audioManagerProvider.get(), this.vibratorProvider.get(), this.flashHelperProvider.get());
    }

    public static ApplicationModule_ProvideAudioHelperFactory create(ApplicationModule applicationModule, Provider<Context> provider, Provider<AudioManager> provider2, Provider<Vibrator> provider3, Provider<FlashHelper> provider4) {
        return new ApplicationModule_ProvideAudioHelperFactory(applicationModule, provider, provider2, provider3, provider4);
    }

    public static AudioHelper provideAudioHelper(ApplicationModule applicationModule, Context context, AudioManager audioManager, Vibrator vibrator, FlashHelper flashHelper) {
        return (AudioHelper) Preconditions.checkNotNullFromProvides(applicationModule.provideAudioHelper(context, audioManager, vibrator, flashHelper));
    }
}
