package com.sca.in_telligent.ui.base;

import android.support.v4.app.Fragment;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

  private static final String TAG = "BasePresenter";

  private final DataManager mDataManager;
  private final SchedulerProvider mSchedulerProvider;
  private final CompositeDisposable mCompositeDisposable;

  @Inject
  TwilioUtil twilioUtil;

  @Inject
  RxPermissions rxPermissions;

  private V mMvpView;

  @Inject
  public BasePresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    this.mDataManager = dataManager;
    this.mSchedulerProvider = schedulerProvider;
    this.mCompositeDisposable = compositeDisposable;
  }

  @Override
  public void onAttach(V mvpView) {
    mMvpView = mvpView;
  }

  @Override
  public void onDetach() {
    mCompositeDisposable.dispose();
    mMvpView = null;
  }

  public boolean isViewAttached() {
    return mMvpView != null;
  }

  public V getMvpView() {
    return mMvpView;
  }

  public void checkViewAttached() {
    if (!isViewAttached()) {
      throw new MvpViewNotAttachedException();
    }
  }

  public DataManager getDataManager() {
    return mDataManager;
  }

  public SchedulerProvider getSchedulerProvider() {
    return mSchedulerProvider;
  }

  public CompositeDisposable getCompositeDisposable() {
    return mCompositeDisposable;
  }

  public RxPermissions getRxPermissions() {
    return rxPermissions;
  }

  public RxPermissions getRxPermissionsFragment(Fragment fragment) {
    return new RxPermissions(fragment);
  }

  public TwilioUtil getTwilioUtil() {
    return twilioUtil;
  }

  public static class MvpViewNotAttachedException extends RuntimeException {

    public MvpViewNotAttachedException() {
      super("Please call Presenter.onAttach(MvpView) before" +
          " requesting data to the Presenter");
    }
  }
}
