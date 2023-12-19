package com.sca.in_telligent.ui.group.member.invite;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.InviteOthersRequest;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class InviteMemberPresenter<V extends InviteMemberMvpView> extends
    BasePresenter<V> implements InviteMemberMvpPresenter<V> {

  @Inject
  public InviteMemberPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void sendInvite(String buildingId, InviteOthersRequest inviteOthersRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().inviteOthers(buildingId, inviteOthersRequest).subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            getMvpView().hideLoading();
            getMvpView().inviteResult(successResponse.isSuccess());
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }
}
