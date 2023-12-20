package com.sca.in_telligent.ui.group.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class GroupListFragment_MembersInjector implements MembersInjector<GroupListFragment> {
    private final Provider<GroupListAdapter> adapterProvider;
    private final Provider<LinearLayoutManager> mLayoutManagerProvider;
    private final Provider<GroupListMvpPresenter<GroupListMvpView>> mPresenterProvider;
    private final Provider<SchedulerProvider> schedulerProvider;
    private final Provider<GroupListSpinnerAdapter> spinnerAdapterProvider;

    public GroupListFragment_MembersInjector(Provider<GroupListMvpPresenter<GroupListMvpView>> provider, Provider<GroupListAdapter> provider2, Provider<GroupListSpinnerAdapter> provider3, Provider<LinearLayoutManager> provider4, Provider<SchedulerProvider> provider5) {
        this.mPresenterProvider = provider;
        this.adapterProvider = provider2;
        this.spinnerAdapterProvider = provider3;
        this.mLayoutManagerProvider = provider4;
        this.schedulerProvider = provider5;
    }

    public static MembersInjector<GroupListFragment> create(Provider<GroupListMvpPresenter<GroupListMvpView>> provider, Provider<GroupListAdapter> provider2, Provider<GroupListSpinnerAdapter> provider3, Provider<LinearLayoutManager> provider4, Provider<SchedulerProvider> provider5) {
        return new GroupListFragment_MembersInjector(provider, provider2, provider3, provider4, provider5);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(GroupListFragment groupListFragment) {
        injectMPresenter(groupListFragment, this.mPresenterProvider.get());
        injectAdapter(groupListFragment, this.adapterProvider.get());
        injectSpinnerAdapter(groupListFragment, this.spinnerAdapterProvider.get());
        injectMLayoutManager(groupListFragment, this.mLayoutManagerProvider.get());
        injectSchedulerProvider(groupListFragment, this.schedulerProvider.get());
    }

    public static void injectMPresenter(GroupListFragment groupListFragment, GroupListMvpPresenter<GroupListMvpView> groupListMvpPresenter) {
        groupListFragment.mPresenter = groupListMvpPresenter;
    }

    public static void injectAdapter(GroupListFragment groupListFragment, GroupListAdapter groupListAdapter) {
        groupListFragment.adapter = groupListAdapter;
    }

    public static void injectSpinnerAdapter(GroupListFragment groupListFragment, GroupListSpinnerAdapter groupListSpinnerAdapter) {
        groupListFragment.spinnerAdapter = groupListSpinnerAdapter;
    }

    public static void injectMLayoutManager(GroupListFragment groupListFragment, LinearLayoutManager linearLayoutManager) {
        groupListFragment.mLayoutManager = linearLayoutManager;
    }

    public static void injectSchedulerProvider(GroupListFragment groupListFragment, SchedulerProvider schedulerProvider) {
        groupListFragment.schedulerProvider = schedulerProvider;
    }
}
