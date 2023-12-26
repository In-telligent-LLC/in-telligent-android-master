package com.sca.in_telligent.ui.base;

import android.media.AudioManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseActivity_MembersInjector implements MembersInjector<BaseActivity> {
    private final Provider<AudioHelper> audioHelperProvider;
    private final Provider<AudioManager> audioManagerProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<FusedLocationProviderClient> fusedLocationProviderClientProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<Responder> responderProvider;
    private final Provider<VideoDownloader> videoDownloaderProvider;

    public BaseActivity_MembersInjector(Provider<AudioManager> provider, Provider<DataManager> provider2, Provider<Responder> provider3, Provider<FusedLocationProviderClient> provider4, Provider<AudioHelper> provider5, Provider<VideoDownloader> provider6, Provider<LocationUtil> provider7) {
        this.audioManagerProvider = provider;
        this.dataManagerProvider = provider2;
        this.responderProvider = provider3;
        this.fusedLocationProviderClientProvider = provider4;
        this.audioHelperProvider = provider5;
        this.videoDownloaderProvider = provider6;
        this.locationUtilProvider = provider7;
    }

    public static MembersInjector<BaseActivity> create(Provider<AudioManager> provider, Provider<DataManager> provider2, Provider<Responder> provider3, Provider<FusedLocationProviderClient> provider4, Provider<AudioHelper> provider5, Provider<VideoDownloader> provider6, Provider<LocationUtil> provider7) {
        return new BaseActivity_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(BaseActivity baseActivity) {
        injectAudioManager(baseActivity, this.audioManagerProvider.get());
        injectDataManager(baseActivity, this.dataManagerProvider.get());
        injectResponder(baseActivity, this.responderProvider.get());
        injectFusedLocationProviderClient(baseActivity, this.fusedLocationProviderClientProvider.get());
        injectAudioHelper(baseActivity, this.audioHelperProvider.get());
        injectVideoDownloader(baseActivity, this.videoDownloaderProvider.get());
        injectLocationUtil(baseActivity, this.locationUtilProvider.get());
    }

    public static void injectAudioManager(BaseActivity baseActivity, AudioManager audioManager) {
        baseActivity.audioManager = audioManager;
    }

    public static void injectDataManager(BaseActivity baseActivity, DataManager dataManager) {
        baseActivity.dataManager = dataManager;
    }

    public static void injectResponder(BaseActivity baseActivity, Responder responder) {
        baseActivity.responder = responder;
    }

    public static void injectFusedLocationProviderClient(BaseActivity baseActivity, FusedLocationProviderClient fusedLocationProviderClient) {
        baseActivity.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    public static void injectAudioHelper(BaseActivity baseActivity, AudioHelper audioHelper) {
        baseActivity.audioHelper = audioHelper;
    }

    public static void injectVideoDownloader(BaseActivity baseActivity, VideoDownloader videoDownloader) {
        baseActivity.videoDownloader = videoDownloader;
    }

    public static void injectLocationUtil(BaseActivity baseActivity, LocationUtil locationUtil) {
        baseActivity.locationUtil = locationUtil;
    }
}
