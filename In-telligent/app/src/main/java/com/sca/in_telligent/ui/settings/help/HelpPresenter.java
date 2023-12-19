package com.sca.in_telligent.ui.settings.help;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.SupportRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

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
//    getRxPermissions()
//        .request(permission.CALL_PHONE)
//        .subscribe(granted -> getMvpView().phonePermissionResult(granted));
  }

  @Override
  public void sendSupportMail(SupportRequest supportRequest) {
    getCompositeDisposable().add(
        getDataManager().sendSupportMail(supportRequest).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            getMvpView().hideLoading();
            getMvpView().messageSent(successResponse.isSuccess());

          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }
}
