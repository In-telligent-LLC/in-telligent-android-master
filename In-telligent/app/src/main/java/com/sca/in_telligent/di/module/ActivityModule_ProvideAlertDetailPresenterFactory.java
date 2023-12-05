package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.group.alert.detail.AlertDetailMvpPresenter;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailMvpView;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideAlertDetailPresenterFactory implements Factory<AlertDetailMvpPresenter<AlertDetailMvpView>> {
    private final ActivityModule module;
    private final Provider<AlertDetailPresenter<AlertDetailMvpView>> presenterProvider;

    public ActivityModule_ProvideAlertDetailPresenterFactory(ActivityModule activityModule, Provider<AlertDetailPresenter<AlertDetailMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public AlertDetailMvpPresenter<AlertDetailMvpView> get() {
        return provideAlertDetailPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideAlertDetailPresenterFactory create(ActivityModule activityModule, Provider<AlertDetailPresenter<AlertDetailMvpView>> provider) {
        return new ActivityModule_ProvideAlertDetailPresenterFactory(activityModule, provider);
    }

    public static AlertDetailMvpPresenter<AlertDetailMvpView> provideAlertDetailPresenter(ActivityModule activityModule, AlertDetailPresenter<AlertDetailMvpView> alertDetailPresenter) {
        return (AlertDetailMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideAlertDetailPresenter(alertDetailPresenter));
    }
}
