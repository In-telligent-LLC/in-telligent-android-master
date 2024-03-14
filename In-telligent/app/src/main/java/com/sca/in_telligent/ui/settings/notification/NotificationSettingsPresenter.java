package com.sca.in_telligent.ui.settings.notification;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class NotificationSettingsPresenter<V extends NotificationSettingsMvpView> extends
    BasePresenter<V> implements NotificationSettingsMvpPresenter<V> {

  @Inject
  public NotificationSettingsPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void syncAlertSubscription(AlertSubscriptionRequest alertSubscriptionRequest) {
    getCompositeDisposable().add(getDataManager().syncAlertSubscription(alertSubscriptionRequest)
        .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
        .subscribe(successResponse -> {
          if (successResponse.isSuccess()) {
            getMvpView().subscriptonUpdated(alertSubscriptionRequest.getBuildingId(),
                alertSubscriptionRequest.getSubscription());
          }
        }, throwable -> {
            getMvpView().onError(throwable.getMessage());

        }));
  }

  @Override
  public void syncMetadata(UpdateSubscriberRequest updateSubscriberRequest) {
    getCompositeDisposable().add(getDataManager().updateUser(updateSubscriberRequest)
        .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(subscriberResponse -> {
              if (subscriberResponse.isSuccess()) {
                getMvpView().subscriberUpdateResult(subscriberResponse.getSubscriber());
              }
            }, throwable -> {
                getMvpView().onError(throwable.getMessage());

            }));
  }
}
