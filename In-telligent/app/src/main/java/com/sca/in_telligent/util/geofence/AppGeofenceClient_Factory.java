package com.sca.in_telligent.util.geofence;

import android.content.Context;
import com.google.android.gms.location.GeofencingClient;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideCompositeDisposableFactory;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppGeofenceClient_Factory implements Factory<AppGeofenceClient> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<Context> contextProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<GeofencingClient> geofencingClientProvider;
    private final Provider<LocationUtil> locationUtilProvider;
    private final Provider<SchedulerProvider> schedulerProvider;

    public AppGeofenceClient_Factory(Provider<GeofencingClient> provider, Provider<LocationUtil> provider2, Provider<DataManager> provider3, Provider<SchedulerProvider> provider4, Provider<CompositeDisposable> provider5, Provider<Context> provider6) {
        this.geofencingClientProvider = provider;
        this.locationUtilProvider = provider2;
        this.dataManagerProvider = provider3;
        this.schedulerProvider = provider4;
        this.compositeDisposableProvider = provider5;
        this.contextProvider = provider6;
    }

    @Override // javax.inject.Provider
    public AppGeofenceClient get() {
        return newInstance(this.geofencingClientProvider.get(), this.locationUtilProvider.get(), this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get(), this.contextProvider.get());
    }

    public static AppGeofenceClient_Factory create(Provider<GeofencingClient> provider, Provider<LocationUtil> provider2, Provider<DataManager> provider3, Provider<SchedulerProvider> provider4, ApplicationModule_ProvideCompositeDisposableFactory provider5, Provider<Context> provider6) {
        return new AppGeofenceClient_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static AppGeofenceClient newInstance(GeofencingClient geofencingClient, LocationUtil locationUtil, DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, Context context) {
        return new AppGeofenceClient(geofencingClient, locationUtil, dataManager, schedulerProvider, compositeDisposable, context);
    }
}
