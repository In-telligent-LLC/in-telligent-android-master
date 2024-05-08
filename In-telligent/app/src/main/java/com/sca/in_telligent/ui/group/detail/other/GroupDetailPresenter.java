package com.sca.in_telligent.ui.group.detail.other;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class GroupDetailPresenter<V extends GroupDetailMvpView> extends BasePresenter<V> implements GroupDetailMvpPresenter<V> {
    @Inject
    public GroupDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void disconnectGroup(UpdateSubscriptionRequest updateSubscriptionRequest) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateSubscription(updateSubscriptionRequest).
                subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
                        successResponse -> {
                            getMvpView().hideLoading();
                            if (successResponse.isSuccess()) {
                                getMvpView().unsubscribed(updateSubscriptionRequest.getBuildingId());
                            } else {
                                if (successResponse.getErrors() != null) {
                                    getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                                }
                            }
                        }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void connectGroup(UpdateSubscriptionRequest updateSubscriptionRequest) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateSubscription(updateSubscriptionRequest).
                subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
                        successResponse -> {
                            getMvpView().hideLoading();
                            if (successResponse.isSuccess()) {
                                getMvpView().subscribed(updateSubscriptionRequest.getBuildingId());
                            } else {
                                if (successResponse.getErrors() != null) {
                                    getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                                }
                            }
                        }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void loadNotifications(int buildingId){
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getAllNotifications(String.valueOf(buildingId)).
                subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
                        successResponse -> {
                            getMvpView().hideLoading();
                            getMvpView().loadNotifications(successResponse.getNotifications());
                            Log.d("getNotificationsPresenter", successResponse.getNotifications().toString());
                        }, throwable -> getMvpView().hideLoading()));

    }

}
