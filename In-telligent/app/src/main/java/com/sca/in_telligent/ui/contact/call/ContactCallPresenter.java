package com.sca.in_telligent.ui.contact.call;

import android.Manifest.permission;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class ContactCallPresenter<V extends ContactCallMvpView> extends
    BasePresenter<V> implements ContactCallMvpPresenter<V> {

  @Inject
  public ContactCallPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void requestRecordAudioPermission() {
    getRxPermissions()
        .request(permission.RECORD_AUDIO)
        .subscribe(granted -> getMvpView().recordAudioPermissionResult(granted));
  }
}
