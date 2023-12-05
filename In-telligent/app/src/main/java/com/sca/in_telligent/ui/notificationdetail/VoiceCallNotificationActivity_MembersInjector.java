package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.openapi.util.AudioHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class VoiceCallNotificationActivity_MembersInjector implements MembersInjector<VoiceCallNotificationActivity> {
    private final Provider<AudioHelper> audioHelperProvider;

    public VoiceCallNotificationActivity_MembersInjector(Provider<AudioHelper> provider) {
        this.audioHelperProvider = provider;
    }

    public static MembersInjector<VoiceCallNotificationActivity> create(Provider<AudioHelper> provider) {
        return new VoiceCallNotificationActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(VoiceCallNotificationActivity voiceCallNotificationActivity) {
        injectAudioHelper(voiceCallNotificationActivity, this.audioHelperProvider.get());
    }

    public static void injectAudioHelper(VoiceCallNotificationActivity voiceCallNotificationActivity, AudioHelper audioHelper) {
        voiceCallNotificationActivity.audioHelper = audioHelper;
    }
}
