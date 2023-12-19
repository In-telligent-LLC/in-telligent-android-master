package com.sca.in_telligent.ui.settings;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements
        SettingsMvpPresenter<V> {

    @Inject
    public SettingsPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void logout() {
        String token = getDataManager().getPushToken();
        getDataManager().setUserAsLoggedOut();
        getDataManager().setLastFetchedGeofences(0);
        if (token != null) {
            getDataManager().setPushToken(token);
        }
        getMvpView().gotoLogin();
    }
}
