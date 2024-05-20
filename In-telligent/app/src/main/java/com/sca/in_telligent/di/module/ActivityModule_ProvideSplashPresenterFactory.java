package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.splash.SplashMvpPresenter;
import com.sca.in_telligent.ui.splash.SplashMvpView;
import com.sca.in_telligent.ui.splash.SplashPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSplashPresenterFactory implements Factory<SplashMvpPresenter<SplashMvpView>> {
    private final ActivityModule module;
    private final Provider<SplashPresenter<SplashMvpView>> presenterProvider;

    public ActivityModule_ProvideSplashPresenterFactory(ActivityModule activityModule, Provider<SplashPresenter<SplashMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public SplashMvpPresenter<SplashMvpView> get() {
        return provideSplashPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideSplashPresenterFactory create(ActivityModule activityModule, Provider<SplashPresenter<SplashMvpView>> provider) {
        return new ActivityModule_ProvideSplashPresenterFactory(activityModule, provider);
    }

    public static SplashMvpPresenter<SplashMvpView> provideSplashPresenter(ActivityModule activityModule, SplashPresenter<SplashMvpView> splashPresenter) {
        return (SplashMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideSplashPresenter(splashPresenter));
    }
}
