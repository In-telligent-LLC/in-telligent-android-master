package com.sca.in_telligent.service;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.MembersInjector;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

public final class GeofencesUpdateService_MembersInjector implements MembersInjector<GeofencesUpdateService> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<SchedulerProvider> schedulerProvider;

    public GeofencesUpdateService_MembersInjector(Provider<DataManager> provider, Provider<LocationUtil> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4) {
        this.dataManagerProvider = provider;
        this.locationUtilProvider = provider2;
        this.schedulerProvider = provider3;
        this.compositeDisposableProvider = provider4;
    }

    public static MembersInjector<GeofencesUpdateService> create(Provider<DataManager> provider, Provider<LocationUtil> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4) {
        return new GeofencesUpdateService_MembersInjector(provider, provider2, provider3, provider4);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(GeofencesUpdateService geofencesUpdateService) {
        injectDataManager(geofencesUpdateService, this.dataManagerProvider.get());
        injectLocationUtil(geofencesUpdateService, this.locationUtilProvider.get());
        injectSchedulerProvider(geofencesUpdateService, this.schedulerProvider.get());
        injectCompositeDisposable(geofencesUpdateService, this.compositeDisposableProvider.get());
    }

    public static void injectDataManager(GeofencesUpdateService geofencesUpdateService, DataManager dataManager) {
        geofencesUpdateService.dataManager = dataManager;
    }

    public static void injectLocationUtil(GeofencesUpdateService geofencesUpdateService, LocationUtil locationUtil) {
        geofencesUpdateService.locationUtil = locationUtil;
    }

    public static void injectSchedulerProvider(GeofencesUpdateService geofencesUpdateService, SchedulerProvider schedulerProvider) {
        geofencesUpdateService.schedulerProvider = schedulerProvider;
    }

    public static void injectCompositeDisposable(GeofencesUpdateService geofencesUpdateService, CompositeDisposable compositeDisposable) {
        geofencesUpdateService.compositeDisposable = compositeDisposable;
    }
}
