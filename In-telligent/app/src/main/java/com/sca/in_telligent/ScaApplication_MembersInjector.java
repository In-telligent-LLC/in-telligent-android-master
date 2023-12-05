package com.sca.in_telligent;

import android.media.AudioManager;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.LifecycleInterface;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ScaApplication_MembersInjector implements MembersInjector<ScaApplication> {
    private final Provider<LifecycleInterface> appLifecycleObserverProvider;
    private final Provider<AudioManager> audioManagerProvider;
    private final Provider<DataManager> mDataManagerProvider;

    public ScaApplication_MembersInjector(Provider<DataManager> provider, Provider<LifecycleInterface> provider2, Provider<AudioManager> provider3) {
        this.mDataManagerProvider = provider;
        this.appLifecycleObserverProvider = provider2;
        this.audioManagerProvider = provider3;
    }

    public static MembersInjector<ScaApplication> create(Provider<DataManager> provider, Provider<LifecycleInterface> provider2, Provider<AudioManager> provider3) {
        return new ScaApplication_MembersInjector(provider, provider2, provider3);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(ScaApplication scaApplication) {
        injectMDataManager(scaApplication, this.mDataManagerProvider.get());
        injectAppLifecycleObserver(scaApplication, this.appLifecycleObserverProvider.get());
        injectAudioManager(scaApplication, this.audioManagerProvider.get());
    }

    public static void injectMDataManager(ScaApplication scaApplication, DataManager dataManager) {
        scaApplication.mDataManager = dataManager;
    }

    public static void injectAppLifecycleObserver(ScaApplication scaApplication, LifecycleInterface lifecycleInterface) {
        scaApplication.appLifecycleObserver = lifecycleInterface;
    }

    public static void injectAudioManager(ScaApplication scaApplication, AudioManager audioManager) {
        scaApplication.audioManager = audioManager;
    }
}
