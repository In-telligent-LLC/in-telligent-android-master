package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.logout.LogoutMvpPresenter;
import com.sca.in_telligent.ui.auth.logout.LogoutMvpView;
import com.sca.in_telligent.ui.auth.logout.LogoutPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideLogoutPresenterFactory implements Factory<LogoutMvpPresenter<LogoutMvpView>> {
    private final ActivityModule module;
    private final Provider<LogoutPresenter<LogoutMvpView>> presenterProvider;

    public ActivityModule_ProvideLogoutPresenterFactory(ActivityModule activityModule, Provider<LogoutPresenter<LogoutMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public LogoutMvpPresenter<LogoutMvpView> get() {
        return provideLogoutPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideLogoutPresenterFactory create(ActivityModule activityModule, Provider<LogoutPresenter<LogoutMvpView>> provider) {
        return new ActivityModule_ProvideLogoutPresenterFactory(activityModule, provider);
    }

    public static LogoutMvpPresenter<LogoutMvpView> provideLogoutPresenter(ActivityModule activityModule, LogoutPresenter<LogoutMvpView> logoutPresenter) {
        return (LogoutMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideLogoutPresenter(logoutPresenter));
    }
}
