package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideLocationProviderClientFactory implements Factory<FusedLocationProviderClient> {
    private final Provider<AppCompatActivity> activityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideLocationProviderClientFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.activityProvider = provider;
    }

    @Override // javax.inject.Provider
    public FusedLocationProviderClient get() {
        return provideLocationProviderClient(this.module, this.activityProvider.get());
    }

    public static ActivityModule_ProvideLocationProviderClientFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideLocationProviderClientFactory(activityModule, provider);
    }

    public static FusedLocationProviderClient provideLocationProviderClient(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (FusedLocationProviderClient) Preconditions.checkNotNullFromProvides(activityModule.provideLocationProviderClient(appCompatActivity));
    }
}
