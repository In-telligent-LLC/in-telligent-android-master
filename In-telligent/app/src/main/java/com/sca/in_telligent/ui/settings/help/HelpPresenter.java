package com.sca.in_telligent.ui.settings.help;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.SupportRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HelpPresenter<V extends HelpMvpView> extends BasePresenter<V> implements
    HelpMvpPresenter<V> {

  @Inject
  public HelpPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void requestPhonePermission() {
//    getMvpView().requestPhonePermission();

  }

  @Override
  public void sendSupportMail(SupportRequest supportRequest) {
    getCompositeDisposable().add(
        getDataManager().sendSupportMail(supportRequest).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
              getMvpView().hideLoading();
              getMvpView().messageSent(successResponse.isSuccess());

            }, throwable -> getMvpView().onError(throwable.getMessage())));
  }
}
