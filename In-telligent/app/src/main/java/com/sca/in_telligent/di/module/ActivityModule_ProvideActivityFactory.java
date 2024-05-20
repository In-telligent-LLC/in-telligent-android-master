package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideActivityFactory implements Factory<AppCompatActivity> {
    private final ActivityModule module;

    public ActivityModule_ProvideActivityFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    @Override // javax.inject.Provider
    public AppCompatActivity get() {
        return provideActivity(this.module);
    }

    public static ActivityModule_ProvideActivityFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvideActivityFactory(activityModule);
    }

    public static AppCompatActivity provideActivity(ActivityModule activityModule) {
        return (AppCompatActivity) Preconditions.checkNotNullFromProvides(activityModule.provideActivity());
    }
}
