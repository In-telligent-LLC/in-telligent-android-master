package com.sca.in_telligent.di.module;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public final class ActivityModule_ProvideCompositeDisposableFactory implements Factory<CompositeDisposable> {
    private final ActivityModule module;

    public ActivityModule_ProvideCompositeDisposableFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    @Override
    public CompositeDisposable get() {
        return provideCompositeDisposable(this.module);
    }

    public static ActivityModule_ProvideCompositeDisposableFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvideCompositeDisposableFactory(activityModule);
    }

    public static CompositeDisposable provideCompositeDisposable(ActivityModule activityModule) {
        return activityModule.provideCompositeDisposable();
    }
}
