package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordMvpView;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideForgotPasswordPresenterFactory implements Factory<ForgotPasswordMvpPresenter<ForgotPasswordMvpView>> {
    private final ActivityModule module;
    private final Provider<ForgotPasswordPresenter<ForgotPasswordMvpView>> presenterProvider;

    public ActivityModule_ProvideForgotPasswordPresenterFactory(ActivityModule activityModule, Provider<ForgotPasswordPresenter<ForgotPasswordMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ForgotPasswordMvpPresenter<ForgotPasswordMvpView> get() {
        return provideForgotPasswordPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideForgotPasswordPresenterFactory create(ActivityModule activityModule, Provider<ForgotPasswordPresenter<ForgotPasswordMvpView>> provider) {
        return new ActivityModule_ProvideForgotPasswordPresenterFactory(activityModule, provider);
    }

    public static ForgotPasswordMvpPresenter<ForgotPasswordMvpView> provideForgotPasswordPresenter(ActivityModule activityModule, ForgotPasswordPresenter<ForgotPasswordMvpView> forgotPasswordPresenter) {
        return (ForgotPasswordMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideForgotPasswordPresenter(forgotPasswordPresenter));
    }
}
