package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideLinearLayoutManagerFactory implements Factory<LinearLayoutManager> {
    private final Provider<AppCompatActivity> activityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideLinearLayoutManagerFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.activityProvider = provider;
    }

    @Override // javax.inject.Provider
    public LinearLayoutManager get() {
        return provideLinearLayoutManager(this.module, this.activityProvider.get());
    }

    public static ActivityModule_ProvideLinearLayoutManagerFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideLinearLayoutManagerFactory(activityModule, provider);
    }

    public static LinearLayoutManager provideLinearLayoutManager(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (LinearLayoutManager) Preconditions.checkNotNullFromProvides(activityModule.provideLinearLayoutManager(appCompatActivity));
    }
}
