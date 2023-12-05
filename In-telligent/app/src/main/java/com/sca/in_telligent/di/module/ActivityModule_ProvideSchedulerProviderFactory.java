package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSchedulerProviderFactory implements Factory<SchedulerProvider> {
    private final ActivityModule module;

    public ActivityModule_ProvideSchedulerProviderFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    @Override // javax.inject.Provider
    public SchedulerProvider get() {
        return provideSchedulerProvider(this.module);
    }

    public static ActivityModule_ProvideSchedulerProviderFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvideSchedulerProviderFactory(activityModule);
    }

    public static SchedulerProvider provideSchedulerProvider(ActivityModule activityModule) {
        return (SchedulerProvider) Preconditions.checkNotNullFromProvides(activityModule.provideSchedulerProvider());
    }
}
