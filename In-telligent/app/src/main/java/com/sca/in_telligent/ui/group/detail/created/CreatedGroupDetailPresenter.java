package com.sca.in_telligent.ui.group.detail.created;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class CreatedGroupDetailPresenter<V extends CreatedGroupDetailMvpView> extends
    BasePresenter<V> implements
    CreatedGroupDetailMvpPresenter<V> {

  @Inject
  public CreatedGroupDetailPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void deletePersonalCommunity(String buildingId) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().deletePersonalCommunity(buildingId)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            getMvpView().hideLoading();
            if (successResponse.isSuccess()) {
              getMvpView().groupDeleted();
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        })
    );
  }
}
