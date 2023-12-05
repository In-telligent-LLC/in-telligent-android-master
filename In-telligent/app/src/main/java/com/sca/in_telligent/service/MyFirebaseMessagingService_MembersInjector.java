package com.sca.in_telligent.service;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class MyFirebaseMessagingService_MembersInjector implements MembersInjector<MyFirebaseMessagingService> {
    private final Provider<AudioHelper> audioHelperProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<GeofenceClient> geofenceClientProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<Responder> responderProvider;
    private final Provider<WeatherUtil> weatherUtilProvider;

    public MyFirebaseMessagingService_MembersInjector(Provider<DataManager> provider, Provider<AudioHelper> provider2, Provider<WeatherUtil> provider3, Provider<Responder> provider4, Provider<LocationUtil> provider5, Provider<GeofenceClient> provider6) {
        this.dataManagerProvider = provider;
        this.audioHelperProvider = provider2;
        this.weatherUtilProvider = provider3;
        this.responderProvider = provider4;
        this.locationUtilProvider = provider5;
        this.geofenceClientProvider = provider6;
    }

    public static MembersInjector<MyFirebaseMessagingService> create(Provider<DataManager> provider, Provider<AudioHelper> provider2, Provider<WeatherUtil> provider3, Provider<Responder> provider4, Provider<LocationUtil> provider5, Provider<GeofenceClient> provider6) {
        return new MyFirebaseMessagingService_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(MyFirebaseMessagingService myFirebaseMessagingService) {
        injectDataManager(myFirebaseMessagingService, this.dataManagerProvider.get());
        injectAudioHelper(myFirebaseMessagingService, this.audioHelperProvider.get());
        injectWeatherUtil(myFirebaseMessagingService, this.weatherUtilProvider.get());
        injectResponder(myFirebaseMessagingService, this.responderProvider.get());
        injectLocationUtil(myFirebaseMessagingService, this.locationUtilProvider.get());
        injectGeofenceClient(myFirebaseMessagingService, this.geofenceClientProvider.get());
    }

    public static void injectDataManager(MyFirebaseMessagingService myFirebaseMessagingService, DataManager dataManager) {
        myFirebaseMessagingService.dataManager = dataManager;
    }

    public static void injectAudioHelper(MyFirebaseMessagingService myFirebaseMessagingService, AudioHelper audioHelper) {
        myFirebaseMessagingService.audioHelper = audioHelper;
    }

    public static void injectWeatherUtil(MyFirebaseMessagingService myFirebaseMessagingService, WeatherUtil weatherUtil) {
        myFirebaseMessagingService.weatherUtil = weatherUtil;
    }

    public static void injectResponder(MyFirebaseMessagingService myFirebaseMessagingService, Responder responder) {
        myFirebaseMessagingService.responder = responder;
    }

    public static void injectLocationUtil(MyFirebaseMessagingService myFirebaseMessagingService, LocationUtil locationUtil) {
        myFirebaseMessagingService.locationUtil = locationUtil;
    }

    public static void injectGeofenceClient(MyFirebaseMessagingService myFirebaseMessagingService, GeofenceClient geofenceClient) {
        myFirebaseMessagingService.geofenceClient = geofenceClient;
    }
}
