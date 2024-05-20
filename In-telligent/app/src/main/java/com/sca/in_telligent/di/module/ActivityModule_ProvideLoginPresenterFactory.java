package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.login.LoginMvpPresenter;
import com.sca.in_telligent.ui.auth.login.LoginMvpView;
import com.sca.in_telligent.ui.auth.login.LoginPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideLoginPresenterFactory implements Factory<LoginMvpPresenter<LoginMvpView>> {
    private final ActivityModule module;
    private final Provider<LoginPresenter<LoginMvpView>> presenterProvider;

    public ActivityModule_ProvideLoginPresenterFactory(ActivityModule activityModule, Provider<LoginPresenter<LoginMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public LoginMvpPresenter<LoginMvpView> get() {
        return provideLoginPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideLoginPresenterFactory create(ActivityModule activityModule, Provider<LoginPresenter<LoginMvpView>> provider) {
        return new ActivityModule_ProvideLoginPresenterFactory(activityModule, provider);
    }

    public static LoginMvpPresenter<LoginMvpView> provideLoginPresenter(ActivityModule activityModule, LoginPresenter<LoginMvpView> loginPresenter) {
        return (LoginMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideLoginPresenter(loginPresenter));
    }
}
