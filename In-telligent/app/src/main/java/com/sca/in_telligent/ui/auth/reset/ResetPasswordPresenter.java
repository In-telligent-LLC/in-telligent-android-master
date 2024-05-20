package com.sca.in_telligent.ui.auth.reset;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.openapi.data.network.model.ResetPasswordRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ResetPasswordPresenter<V extends ResetPasswordMvpView> extends
    BasePresenter<V> implements ResetPasswordMvpPresenter<V> {

  @Inject
  public ResetPasswordPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void submit(ResetPasswordRequest resetPasswordRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(getDataManager().resetPassword(resetPasswordRequest).
        subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
        new Consumer<LoginResponse>() {
          @Override
          public void accept(LoginResponse loginResponse) throws Exception {
            getMvpView().hideLoading();
            if (loginResponse.isSuccess()) {
              getDataManager()
                  .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
              getMvpView().passwordChanged();
            } else {
              if (loginResponse.getError() != null) {
                getMvpView().showMessage(loginResponse.getError());
              }
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }
}
