package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.intro.IntroMvpPresenter;
import com.sca.in_telligent.ui.intro.IntroMvpView;
import com.sca.in_telligent.ui.intro.IntroPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideIntroPresenterFactory implements Factory<IntroMvpPresenter<IntroMvpView>> {
    private final ActivityModule module;
    private final Provider<IntroPresenter<IntroMvpView>> presenterProvider;

    public ActivityModule_ProvideIntroPresenterFactory(ActivityModule activityModule, Provider<IntroPresenter<IntroMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public IntroMvpPresenter<IntroMvpView> get() {
        return provideIntroPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideIntroPresenterFactory create(ActivityModule activityModule, Provider<IntroPresenter<IntroMvpView>> provider) {
        return new ActivityModule_ProvideIntroPresenterFactory(activityModule, provider);
    }

    public static IntroMvpPresenter<IntroMvpView> provideIntroPresenter(ActivityModule activityModule, IntroPresenter<IntroMvpView> introPresenter) {
        return (IntroMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideIntroPresenter(introPresenter));
    }
}
