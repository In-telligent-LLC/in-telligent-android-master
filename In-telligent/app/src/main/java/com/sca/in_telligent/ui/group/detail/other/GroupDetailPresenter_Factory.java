package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

public final class GroupDetailPresenter_Factory<V extends GroupDetailMvpView> implements Factory<GroupDetailPresenter<V>> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<SchedulerProvider> schedulerProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

    public GroupDetailPresenter_Factory(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4) {
        this.dataManagerProvider = provider;
        this.schedulerProvider = provider2;
        this.compositeDisposableProvider = provider3;
        this.twilioUtilProvider = provider4;
    }

    @Override // javax.inject.Provider
    public GroupDetailPresenter<V> get() {
        GroupDetailPresenter<V> newInstance = newInstance(this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get());
        BasePresenter_MembersInjector.injectTwilioUtil(newInstance, this.twilioUtilProvider.get());
        return newInstance;
    }

    public static <V extends GroupDetailMvpView> GroupDetailPresenter_Factory<V> create(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4) {
        return new GroupDetailPresenter_Factory<>(provider, provider2, provider3, provider4);
    }

    public static <V extends GroupDetailMvpView> GroupDetailPresenter<V> newInstance(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new GroupDetailPresenter<>(dataManager, schedulerProvider, compositeDisposable);
    }
}
