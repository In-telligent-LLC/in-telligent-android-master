package com.sca.in_telligent.ui.auth.forgot;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.ForgotPasswordRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ForgotPasswordPresenter<V extends ForgotPasswordMvpView> extends
    BasePresenter<V> implements ForgotPasswordMvpPresenter<V> {

  @Inject
  public ForgotPasswordPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void submitEmail(ForgotPasswordRequest forgotPasswordRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(getDataManager().forgotPassword(forgotPasswordRequest).
        subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
        new Consumer<LoginResponse>() {
          @Override
          public void accept(LoginResponse loginResponse) throws Exception {
            getMvpView().hideLoading();
            getMvpView().showSubmitToast();
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }
}
