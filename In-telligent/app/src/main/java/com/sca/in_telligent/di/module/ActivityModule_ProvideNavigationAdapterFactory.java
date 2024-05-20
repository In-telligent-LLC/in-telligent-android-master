package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.main.NavigationDrawerAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideNavigationAdapterFactory implements Factory<NavigationDrawerAdapter> {
    private final Provider<AppCompatActivity> activityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideNavigationAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.activityProvider = provider;
    }

    @Override // javax.inject.Provider
    public NavigationDrawerAdapter get() {
        return provideNavigationAdapter(this.module, this.activityProvider.get());
    }

    public static ActivityModule_ProvideNavigationAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideNavigationAdapterFactory(activityModule, provider);
    }

    public static NavigationDrawerAdapter provideNavigationAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (NavigationDrawerAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideNavigationAdapter(appCompatActivity));
    }
}
