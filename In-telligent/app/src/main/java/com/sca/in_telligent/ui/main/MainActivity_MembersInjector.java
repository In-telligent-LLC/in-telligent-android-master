package com.sca.in_telligent.ui.main;

import android.media.AudioManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.WorkManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.ui.base.BaseActivity_MembersInjector;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
    private final Provider<NavigationDrawerAdapter> adapterProvider;
    private final Provider<AudioHelper> audioHelperProvider;
    private final Provider<AudioManager> audioManagerProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<WorkManager> firebaseJobDispatcherProvider;
    private final Provider<FusedLocationProviderClient> fusedLocationProviderClientProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<GeofenceClient> mGeofenceClientProvider;
    private final Provider<LinearLayoutManager> mLayoutManagerProvider;
    private final Provider<MainMvpPresenter<MainMvpView>> mPresenterProvider;
    private final Provider<Responder> responderProvider;
    private final Provider<VideoDownloader> videoDownloaderProvider;

    public MainActivity_MembersInjector(Provider<AudioManager> provider, Provider<DataManager> provider2, Provider<Responder> provider3, Provider<FusedLocationProviderClient> provider4, Provider<AudioHelper> provider5, Provider<VideoDownloader> provider6, Provider<LocationUtil> provider7, Provider<MainMvpPresenter<MainMvpView>> provider8, Provider<NavigationDrawerAdapter> provider9, Provider<LinearLayoutManager> provider10, Provider<GeofenceClient> provider11, Provider<WorkManager> provider12) {
        this.audioManagerProvider = provider;
        this.dataManagerProvider = provider2;
        this.responderProvider = provider3;
        this.fusedLocationProviderClientProvider = provider4;
        this.audioHelperProvider = provider5;
        this.videoDownloaderProvider = provider6;
        this.locationUtilProvider = provider7;
        this.mPresenterProvider = provider8;
        this.adapterProvider = provider9;
        this.mLayoutManagerProvider = provider10;
        this.mGeofenceClientProvider = provider11;
        this.firebaseJobDispatcherProvider = provider12;
    }

    public static MembersInjector<MainActivity> create(Provider<AudioManager> provider, Provider<DataManager> provider2, Provider<Responder> provider3, Provider<FusedLocationProviderClient> provider4, Provider<AudioHelper> provider5, Provider<VideoDownloader> provider6, Provider<LocationUtil> provider7, Provider<MainMvpPresenter<MainMvpView>> provider8, Provider<NavigationDrawerAdapter> provider9, Provider<LinearLayoutManager> provider10, Provider<GeofenceClient> provider11, Provider<WorkManager> provider12) {
        return new MainActivity_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(MainActivity mainActivity) {
        BaseActivity_MembersInjector.injectAudioManager(mainActivity, this.audioManagerProvider.get());
        BaseActivity_MembersInjector.injectDataManager(mainActivity, this.dataManagerProvider.get());
        BaseActivity_MembersInjector.injectResponder(mainActivity, this.responderProvider.get());
        BaseActivity_MembersInjector.injectFusedLocationProviderClient(mainActivity, this.fusedLocationProviderClientProvider.get());
        BaseActivity_MembersInjector.injectAudioHelper(mainActivity, this.audioHelperProvider.get());
        BaseActivity_MembersInjector.injectVideoDownloader(mainActivity, this.videoDownloaderProvider.get());
        BaseActivity_MembersInjector.injectLocationUtil(mainActivity, this.locationUtilProvider.get());
        injectMPresenter(mainActivity, this.mPresenterProvider.get());
        injectAdapter(mainActivity, this.adapterProvider.get());
        injectMLayoutManager(mainActivity, this.mLayoutManagerProvider.get());
        injectMGeofenceClient(mainActivity, this.mGeofenceClientProvider.get());
        injectFirebaseJobDispatcher(mainActivity, this.firebaseJobDispatcherProvider.get());
    }

    public static void injectMPresenter(MainActivity mainActivity, MainMvpPresenter<MainMvpView> mainMvpPresenter) {
        mainActivity.mPresenter = mainMvpPresenter;
    }

    public static void injectAdapter(MainActivity mainActivity, NavigationDrawerAdapter navigationDrawerAdapter) {
        mainActivity.adapter = navigationDrawerAdapter;
    }

    public static void injectMLayoutManager(MainActivity mainActivity, LinearLayoutManager linearLayoutManager) {
        mainActivity.mLayoutManager = linearLayoutManager;
    }

    public static void injectMGeofenceClient(MainActivity mainActivity, GeofenceClient geofenceClient) {
        mainActivity.mGeofenceClient = geofenceClient;
    }

    public static void injectFirebaseJobDispatcher(MainActivity mainActivity, WorkManager workManagerDispatcher) {
        mainActivity.workManager = workManagerDispatcher;
    }
}
