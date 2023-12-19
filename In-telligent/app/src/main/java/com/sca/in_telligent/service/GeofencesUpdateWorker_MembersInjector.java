package com.sca.in_telligent.service;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Provider;

import dagger.MembersInjector;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public final class GeofencesUpdateWorker_MembersInjector implements MembersInjector<GeofencesUpdateWorker> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<SchedulerProvider> schedulerProvider;


    public GeofencesUpdateWorker_MembersInjector(Provider<CompositeDisposable> compositeDisposableProvider, Provider<DataManager> dataManagerProvider, Provider<LocationUtil> locationUtilProvider, Provider<SchedulerProvider> schedulerProvider) {
        this.compositeDisposableProvider = compositeDisposableProvider;
        this.dataManagerProvider = dataManagerProvider;
        this.locationUtilProvider = locationUtilProvider;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void injectMembers(GeofencesUpdateWorker instance) {
        injectDataManager(instance, this.dataManagerProvider.get());
        injectLocationUtil(instance, this.locationUtilProvider.get());
        injectSchedulerProvider(instance, this.schedulerProvider.get());
        injectCompositeDisposable(instance, this.compositeDisposableProvider.get());

    }

//    public static MembersInjector<GeofencesUpdateService> create(Provider<DataManager> provider, Provider<LocationUtil> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4) {
//        return new GeofencesUpdateService_MembersInjector(provider, provider2, provider3, provider4);
//    }
//
//    @Override // dagger.MembersInjector
//    public void injectMembers(GeofencesUpdateService geofencesUpdateService) {
//        injectDataManager(geofencesUpdateService, this.dataManagerProvider.get());
//        injectLocationUtil(geofencesUpdateService, this.locationUtilProvider.get());
//        injectSchedulerProvider(geofencesUpdateService, this.schedulerProvider.get());
//        injectCompositeDisposable(geofencesUpdateService, this.compositeDisposableProvider.get());
//    }
//
    public static void injectDataManager(GeofencesUpdateWorker geofencesUpdateWorker, DataManager dataManager) {
        geofencesUpdateWorker.dataManager = dataManager;
    }
//
    public static void injectLocationUtil(GeofencesUpdateWorker geofencesUpdateWorker, LocationUtil locationUtil) {
        geofencesUpdateWorker.locationUtil = locationUtil;
    }
//
    public static void injectSchedulerProvider(GeofencesUpdateWorker geofencesUpdateWorker, SchedulerProvider schedulerProvider) {
        geofencesUpdateWorker.schedulerProvider = schedulerProvider;
    }
//
    public static void injectCompositeDisposable(GeofencesUpdateWorker geofencesUpdateWorker, CompositeDisposable compositeDisposable) {
        geofencesUpdateWorker.compositeDisposable = compositeDisposable;
    }
}
