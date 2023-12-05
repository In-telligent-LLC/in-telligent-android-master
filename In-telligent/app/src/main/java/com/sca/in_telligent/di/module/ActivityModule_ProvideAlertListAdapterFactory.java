package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.group.alert.list.AlertListAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideAlertListAdapterFactory implements Factory<AlertListAdapter> {
    private final Provider<AppCompatActivity> activityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideAlertListAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.activityProvider = provider;
    }

    @Override // javax.inject.Provider
    public AlertListAdapter get() {
        return provideAlertListAdapter(this.module, this.activityProvider.get());
    }

    public static ActivityModule_ProvideAlertListAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideAlertListAdapterFactory(activityModule, provider);
    }

    public static AlertListAdapter provideAlertListAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (AlertListAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideAlertListAdapter(appCompatActivity));
    }
}
