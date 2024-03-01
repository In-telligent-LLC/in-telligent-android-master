package com.sca.in_telligent.ui.settings.account;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguageResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

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
                        notificationLanguageResponse -> getMvpView().loadLanguages(notificationLanguageResponse.getLanguages()),
                        throwable -> {
                            getMvpView().onError(throwable.getMessage());

                        }));
  }

  @Override
  public void syncMetadata(UpdateSubscriberRequest updateSubscriberRequest) {
    getCompositeDisposable().add(getDataManager().updateUser(updateSubscriberRequest)
        .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(subscriberResponse -> {
              if (subscriberResponse.isSuccess()) {
                getMvpView().subscriberLanguageResult(subscriberResponse.getSubscriber());
              }
            }, throwable -> {
                getMvpView().onError(throwable.getMessage());
            }));

  }
}
