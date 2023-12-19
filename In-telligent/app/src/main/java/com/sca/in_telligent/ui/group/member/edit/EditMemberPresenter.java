package com.sca.in_telligent.ui.group.member.edit;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.EditCommunityInviteeRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class EditMemberPresenter<V extends EditMemberMvpView> extends BasePresenter<V> implements
    EditMemberMvpPresenter<V> {

  @Inject
  public EditMemberPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void saveMemberEdit(EditCommunityInviteeRequest editCommunityInviteeRequest) {
      getMvpView().showLoading();
      getCompositeDisposable().add(
              getDataManager().editMember(editCommunityInviteeRequest.getInviteeId(),
                      editCommunityInviteeRequest).subscribeOn(getSchedulerProvider().io()).
                      observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
                          getMvpView().hideLoading();
                          getMvpView().editResult(successResponse.isSuccess());
                      }, throwable ->{
                          getMvpView().hideLoading();
                      }));
  }
}
