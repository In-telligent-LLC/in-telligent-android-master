package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.reset.ResetPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordMvpView;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideResetPasswordPresenterFactory implements Factory<ResetPasswordMvpPresenter<ResetPasswordMvpView>> {
    private final ActivityModule module;
    private final Provider<ResetPasswordPresenter<ResetPasswordMvpView>> presenterProvider;

    public ActivityModule_ProvideResetPasswordPresenterFactory(ActivityModule activityModule, Provider<ResetPasswordPresenter<ResetPasswordMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ResetPasswordMvpPresenter<ResetPasswordMvpView> get() {
        return provideResetPasswordPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideResetPasswordPresenterFactory create(ActivityModule activityModule, Provider<ResetPasswordPresenter<ResetPasswordMvpView>> provider) {
        return new ActivityModule_ProvideResetPasswordPresenterFactory(activityModule, provider);
    }

    public static ResetPasswordMvpPresenter<ResetPasswordMvpView> provideResetPasswordPresenter(ActivityModule activityModule, ResetPasswordPresenter<ResetPasswordMvpView> resetPasswordPresenter) {
        return (ResetPasswordMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideResetPasswordPresenter(resetPasswordPresenter));
    }
}
