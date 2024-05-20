package com.sca.in_telligent.ui.settings.account;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguageResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class AccountSettingsPresenter<V extends AccountSettingsMvpView> extends
    BasePresenter<V> implements AccountSettingsMvpPresenter<V> {

  @Inject
  public AccountSettingsPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void listLanguages() {
    getCompositeDisposable().add(
        getDataManager().getLanguages().subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<NotificationLanguageResponse>() {
              @Override
              public void accept(NotificationLanguageResponse notificationLanguageResponse)
                  throws Exception {
                getMvpView().loadLanguages(notificationLanguageResponse.getLanguages());
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
              getMvpView().subscriberLanguageResult(subscriberResponse.getSubscriber());
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        }));

  }
}
