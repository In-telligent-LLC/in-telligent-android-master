package com.sca.in_telligent.di.module;

import android.content.Context;
import com.google.android.gms.location.GeofencingClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideGeofencingClientFactory implements Factory<GeofencingClient> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideGeofencingClientFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public GeofencingClient get() {
        return provideGeofencingClient(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideGeofencingClientFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideGeofencingClientFactory(applicationModule, provider);
    }

    public static GeofencingClient provideGeofencingClient(ApplicationModule applicationModule, Context context) {
        return (GeofencingClient) Preconditions.checkNotNullFromProvides(applicationModule.provideGeofencingClient(context));
    }
}
