package com.sca.in_telligent.ui.popup;

import android.Manifest.permission;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.CallDetailResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class IncomingCallPresenter<V extends IncomingCallMvpView> extends
    BasePresenter<V> implements IncomingCallMvpPresenter<V> {

  @Inject
  public IncomingCallPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void getCallDetail(String conferenceId) {
    getCompositeDisposable()
        .add(getDataManager().getCallDetail(conferenceId).subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<CallDetailResponse>() {
          @Override
          public void accept(CallDetailResponse callDetailResponse) throws Exception {
            getMvpView().callDetail(callDetailResponse.isCompleted());
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        }));
  }

    @Override
    public void requestRecordAudioPermission() {

    }

//  @Override
//  public void requestRecordAudioPermission() {
//
//        .request(permission.RECORD_AUDIO)
//        .subscribe(granted -> getMvpView().recordAudioPermissionResult(granted));
//  }
}
