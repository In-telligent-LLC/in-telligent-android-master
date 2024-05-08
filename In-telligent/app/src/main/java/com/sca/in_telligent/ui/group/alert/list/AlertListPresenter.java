package com.sca.in_telligent.ui.group.alert.list;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.openapi.data.network.model.NotificationsResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class AlertListPresenter<V extends AlertListMvpView> extends BasePresenter<V> implements AlertListMvpPresenter<V> {
    @Inject
    public AlertListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadNotifications(String buildingId, boolean showLoading) {
        if (showLoading) {
            getMvpView().showLoading();
        }
        getCompositeDisposable().add(
                getDataManager().getAllNotifications(buildingId).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(
                                notificationsResponse -> {
                                    getMvpView().hideLoading();
                                    getMvpView().loadNotifications(notificationsResponse.getNotifications());
                                }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void deleteAlert(String notificationId, int position) {
        getMvpView().showLoading();
        AlertDeleteRequest alertDeleteRequest = new AlertDeleteRequest(notificationId);

        getCompositeDisposable().add(
                getDataManager().deleteAlert(alertDeleteRequest).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
                            getMvpView().hideLoading();
                            if (successResponse.isSuccess()) {
                                getMvpView().alertDeleted(position);
                            }
                        }, throwable -> getMvpView().hideLoading())
        );
    }
}
