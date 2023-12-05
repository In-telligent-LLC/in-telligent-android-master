package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.register.SignupPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.register.SignupPasswordMvpView;
import com.sca.in_telligent.ui.auth.register.SignupPasswordPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSignupPasswordPresenterFactory implements Factory<SignupPasswordMvpPresenter<SignupPasswordMvpView>> {
    private final ActivityModule module;
    private final Provider<SignupPasswordPresenter<SignupPasswordMvpView>> presenterProvider;

    public ActivityModule_ProvideSignupPasswordPresenterFactory(ActivityModule activityModule, Provider<SignupPasswordPresenter<SignupPasswordMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public SignupPasswordMvpPresenter<SignupPasswordMvpView> get() {
        return provideSignupPasswordPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideSignupPasswordPresenterFactory create(ActivityModule activityModule, Provider<SignupPasswordPresenter<SignupPasswordMvpView>> provider) {
        return new ActivityModule_ProvideSignupPasswordPresenterFactory(activityModule, provider);
    }

    public static SignupPasswordMvpPresenter<SignupPasswordMvpView> provideSignupPasswordPresenter(ActivityModule activityModule, SignupPasswordPresenter<SignupPasswordMvpView> signupPasswordPresenter) {
        return (SignupPasswordMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideSignupPasswordPresenter(signupPasswordPresenter));
    }
}
