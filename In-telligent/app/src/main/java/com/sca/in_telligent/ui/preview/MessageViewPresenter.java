package com.sca.in_telligent.ui.preview;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;

public class MessageViewPresenter<V extends MessageViewMvpView> extends BasePresenter<V> implements
    MessageViewMvpPresenter<V> {

  private static final String TAG = MessageViewPresenter.class.getSimpleName();

  @Inject
  public MessageViewPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void getNotification(String notificationId) {
    getDataManager().getNotification(notificationId).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(notificationResponse -> {
      getMvpView().notifyNotificationDetailsFetched(notificationResponse.getNotification());
    }, throwable -> {
      Log.e(TAG, "Error fetching the notification");
    });
  }
}
