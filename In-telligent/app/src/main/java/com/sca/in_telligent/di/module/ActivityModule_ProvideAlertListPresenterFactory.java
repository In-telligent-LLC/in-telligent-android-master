package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.group.alert.list.AlertListMvpPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;
import com.sca.in_telligent.ui.group.alert.list.AlertListPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideAlertListPresenterFactory implements Factory<AlertListMvpPresenter<AlertListMvpView>> {
    private final ActivityModule module;
    private final Provider<AlertListPresenter<AlertListMvpView>> presenterProvider;

    public ActivityModule_ProvideAlertListPresenterFactory(ActivityModule activityModule, Provider<AlertListPresenter<AlertListMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public AlertListMvpPresenter<AlertListMvpView> get() {
        return provideAlertListPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideAlertListPresenterFactory create(ActivityModule activityModule, Provider<AlertListPresenter<AlertListMvpView>> provider) {
        return new ActivityModule_ProvideAlertListPresenterFactory(activityModule, provider);
    }

    public static AlertListMvpPresenter<AlertListMvpView> provideAlertListPresenter(ActivityModule activityModule, AlertListPresenter<AlertListMvpView> alertListPresenter) {
        return (AlertListMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideAlertListPresenter(alertListPresenter));
    }
}
