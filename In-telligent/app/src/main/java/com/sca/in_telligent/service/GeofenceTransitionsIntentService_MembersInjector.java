package com.sca.in_telligent.service;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.MembersInjector;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Provider;

public final class GeofenceTransitionsIntentService_MembersInjector implements MembersInjector<GeofenceTransitionsIntentService> {
    private final Provider<CompositeDisposable> mCompositeDisposableProvider;
    private final Provider<DataManager> mDataManagerProvider;
    private final Provider<GeofenceClient> mGeofenceClientProvider;
    private final Provider<LocationUtil> mLocationUtilProvider;
    private final Provider<SchedulerProvider> mSchedulerProvider;
    private final Provider<WeatherUtil> mWeatherUtilProvider;

    public GeofenceTransitionsIntentService_MembersInjector(Provider<LocationUtil> provider, Provider<DataManager> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4, Provider<GeofenceClient> provider5, Provider<WeatherUtil> provider6) {
        this.mLocationUtilProvider = provider;
        this.mDataManagerProvider = provider2;
        this.mSchedulerProvider = provider3;
        this.mCompositeDisposableProvider = provider4;
        this.mGeofenceClientProvider = provider5;
        this.mWeatherUtilProvider = provider6;
    }

    public static MembersInjector<GeofenceTransitionsIntentService> create(Provider<LocationUtil> provider, Provider<DataManager> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4, Provider<GeofenceClient> provider5, Provider<WeatherUtil> provider6) {
        return new GeofenceTransitionsIntentService_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(GeofenceTransitionsIntentService geofenceTransitionsIntentService) {
        injectMLocationUtil(geofenceTransitionsIntentService, this.mLocationUtilProvider.get());
        injectMDataManager(geofenceTransitionsIntentService, this.mDataManagerProvider.get());
        injectMSchedulerProvider(geofenceTransitionsIntentService, this.mSchedulerProvider.get());
        injectMCompositeDisposable(geofenceTransitionsIntentService, this.mCompositeDisposableProvider.get());
        injectMGeofenceClient(geofenceTransitionsIntentService, this.mGeofenceClientProvider.get());
        injectMWeatherUtil(geofenceTransitionsIntentService, this.mWeatherUtilProvider.get());
    }

    public static void injectMLocationUtil(GeofenceTransitionsIntentService geofenceTransitionsIntentService, LocationUtil locationUtil) {
        geofenceTransitionsIntentService.mLocationUtil = locationUtil;
    }

    public static void injectMDataManager(GeofenceTransitionsIntentService geofenceTransitionsIntentService, DataManager dataManager) {
        geofenceTransitionsIntentService.mDataManager = dataManager;
    }

    public static void injectMSchedulerProvider(GeofenceTransitionsIntentService geofenceTransitionsIntentService, SchedulerProvider schedulerProvider) {
        geofenceTransitionsIntentService.mSchedulerProvider = schedulerProvider;
    }

    public static void injectMCompositeDisposable(GeofenceTransitionsIntentService geofenceTransitionsIntentService, CompositeDisposable compositeDisposable) {
        geofenceTransitionsIntentService.mCompositeDisposable = compositeDisposable;
    }

    public static void injectMGeofenceClient(GeofenceTransitionsIntentService geofenceTransitionsIntentService, GeofenceClient geofenceClient) {
        geofenceTransitionsIntentService.mGeofenceClient = geofenceClient;
    }

    public static void injectMWeatherUtil(GeofenceTransitionsIntentService geofenceTransitionsIntentService, WeatherUtil weatherUtil) {
        geofenceTransitionsIntentService.mWeatherUtil = weatherUtil;
    }
}
