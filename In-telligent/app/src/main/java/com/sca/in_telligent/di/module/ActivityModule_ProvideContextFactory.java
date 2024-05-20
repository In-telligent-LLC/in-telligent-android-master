package com.sca.in_telligent.di.module;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContextFactory implements Factory<Context> {
    private final ActivityModule module;

    public ActivityModule_ProvideContextFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideContext(this.module);
    }

    public static ActivityModule_ProvideContextFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvideContextFactory(activityModule);
    }

    public static Context provideContext(ActivityModule activityModule) {
        return (Context) Preconditions.checkNotNullFromProvides(activityModule.provideContext());
    }
}
