package com.sca.in_telligent.ui.contact.call;

import android.Manifest;
import android.annotation.SuppressLint;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ContactCallPresenter<V extends ContactCallMvpView> extends
    BasePresenter<V> implements ContactCallMvpPresenter<V> {

  @Inject
  public ContactCallPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @SuppressLint("CheckResult")
  @Override
  public void requestRecordAudioPermission() {
      getRxPermissions()
              .request(Manifest.permission.RECORD_AUDIO)
              .subscribe(granted -> getMvpView().recordAudioPermissionResult(granted));
    }

}
