package com.sca.in_telligent.ui.inbox;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class InboxPresenter<V extends InboxMvpView> extends BasePresenter<V> implements
        InboxMvpPresenter<V> {

    @Inject
    public InboxPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getInbox(String page) {
        getInbox(page, true);
    }

    @Override
    public void getInbox(String page, boolean showLoading) {
        if (showLoading) {
            getMvpView().showLoading();
        }
        getCompositeDisposable().add(
                getDataManager().getCurrentSubscriberInbox(page).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(notificationResponse -> {
                    getMvpView().hideLoading();
                    getMvpView().loadInbox(notificationResponse.getNotifications(), Integer.parseInt(page));

                }, throwable -> getMvpView().hideLoading()));
    }

    @Override
    public void getSavedMessages() {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getSavedMessages().subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(notificationResponse -> {
                    getMvpView().hideLoading();
                    getMvpView().loadSavedMessages(notificationResponse.getNotifications());
                }, throwable -> getMvpView().hideLoading())
        );
    }

  @Override
  public void deleteAlert(AlertDeleteRequest alertDeleteRequest, int position) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().deleteAlert(alertDeleteRequest).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
              getMvpView().hideLoading();
              if (successResponse.isSuccess()) {
                getMvpView().alertDeleted(position);
              }
            }, throwable -> getMvpView().hideLoading())
    );
  }
}
