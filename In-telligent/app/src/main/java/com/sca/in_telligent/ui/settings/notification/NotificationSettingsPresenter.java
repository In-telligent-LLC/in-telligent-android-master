package com.sca.in_telligent.ui.settings.notification;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

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
        .subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            if (successResponse.isSuccess()) {
              getMvpView().subscriptonUpdated(alertSubscriptionRequest.getBuildingId(),
                  alertSubscriptionRequest.getSubscription());
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        }));
  }

  @Override
  public void syncMetadata(UpdateSubscriberRequest updateSubscriberRequest) {
    getCompositeDisposable().add(getDataManager().updateUser(updateSubscriberRequest)
        .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SubscriberResponse>() {
          @Override
          public void accept(SubscriberResponse subscriberResponse) throws Exception {
            if (subscriberResponse.isSuccess()) {
              getMvpView().subscriberUpdateResult(subscriberResponse.getSubscriber());
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        }));
  }
}
