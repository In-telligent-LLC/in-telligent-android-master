package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.auth.register.SignupDemographicsMvpPresenter;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsMvpView;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSignupDemographicsPresenterFactory implements Factory<SignupDemographicsMvpPresenter<SignupDemographicsMvpView>> {
    private final ActivityModule module;
    private final Provider<SignupDemographicsPresenter<SignupDemographicsMvpView>> presenterProvider;

    public ActivityModule_ProvideSignupDemographicsPresenterFactory(ActivityModule activityModule, Provider<SignupDemographicsPresenter<SignupDemographicsMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public SignupDemographicsMvpPresenter<SignupDemographicsMvpView> get() {
        return provideSignupDemographicsPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideSignupDemographicsPresenterFactory create(ActivityModule activityModule, Provider<SignupDemographicsPresenter<SignupDemographicsMvpView>> provider) {
        return new ActivityModule_ProvideSignupDemographicsPresenterFactory(activityModule, provider);
    }

    public static SignupDemographicsMvpPresenter<SignupDemographicsMvpView> provideSignupDemographicsPresenter(ActivityModule activityModule, SignupDemographicsPresenter<SignupDemographicsMvpView> signupDemographicsPresenter) {
        return (SignupDemographicsMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideSignupDemographicsPresenter(signupDemographicsPresenter));
    }
}
