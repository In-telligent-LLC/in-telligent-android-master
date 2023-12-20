package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.group.list.GroupListMvpView;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

public final class GroupListPresenter_Factory<V extends GroupListMvpView> implements Factory<GroupListPresenter<V>> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<SchedulerProvider> schedulerProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

    public GroupListPresenter_Factory(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4) {
        this.dataManagerProvider = provider;
        this.schedulerProvider = provider2;
        this.compositeDisposableProvider = provider3;
        this.twilioUtilProvider = provider4;
    }

    @Override // javax.inject.Provider
    public GroupListPresenter<V> get() {
        GroupListPresenter<V> newInstance = newInstance(this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get());
        BasePresenter_MembersInjector.injectTwilioUtil(newInstance, this.twilioUtilProvider.get());
        return newInstance;
    }

    public static <V extends GroupListMvpView> GroupListPresenter_Factory<V> create(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4) {
        return new GroupListPresenter_Factory<>(provider, provider2, provider3, provider4);
    }

    public static <V extends GroupListMvpView> GroupListPresenter<V> newInstance(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new GroupListPresenter<>(dataManager, schedulerProvider, compositeDisposable);
    }
}
