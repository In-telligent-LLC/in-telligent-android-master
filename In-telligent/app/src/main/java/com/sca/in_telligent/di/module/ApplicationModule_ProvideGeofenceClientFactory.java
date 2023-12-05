package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.geofence.AppGeofenceClient;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideGeofenceClientFactory implements Factory<GeofenceClient> {
    private final Provider<AppGeofenceClient> appGeofenceClientProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideGeofenceClientFactory(ApplicationModule applicationModule, Provider<AppGeofenceClient> provider) {
        this.module = applicationModule;
        this.appGeofenceClientProvider = provider;
    }

    @Override // javax.inject.Provider
    public GeofenceClient get() {
        return provideGeofenceClient(this.module, this.appGeofenceClientProvider.get());
    }

    public static ApplicationModule_ProvideGeofenceClientFactory create(ApplicationModule applicationModule, Provider<AppGeofenceClient> provider) {
        return new ApplicationModule_ProvideGeofenceClientFactory(applicationModule, provider);
    }

    public static GeofenceClient provideGeofenceClient(ApplicationModule applicationModule, AppGeofenceClient appGeofenceClient) {
        return (GeofenceClient) Preconditions.checkNotNullFromProvides(applicationModule.provideGeofenceClient(appGeofenceClient));
    }
}
