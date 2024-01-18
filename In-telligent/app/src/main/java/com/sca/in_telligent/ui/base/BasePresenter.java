package com.sca.in_telligent.ui.base;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.TwilioUtil;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private static final String TAG = "BasePresenter";
    private final CompositeDisposable mCompositeDisposable;
    private final DataManager mDataManager;
    private V mMvpView;
    private final SchedulerProvider mSchedulerProvider;
//    @Inject
//    Permission rxPermissions;
    @Inject
    TwilioUtil twilioUtil;

    @Inject
    public BasePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V v) {
        this.mMvpView = v;
    }

    @Override
    public void onDetach() {
        this.mCompositeDisposable.dispose();
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return this.mMvpView != null;
    }

    public V getMvpView() {
        return this.mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public DataManager getDataManager() {
        return this.mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return this.mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    public TwilioUtil getTwilioUtil() {
        return this.twilioUtil;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before requesting data to the Presenter");
        }
    }
}
