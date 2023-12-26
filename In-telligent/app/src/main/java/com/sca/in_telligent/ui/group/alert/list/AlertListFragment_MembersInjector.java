package com.sca.in_telligent.ui.group.alert.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AlertListFragment_MembersInjector implements MembersInjector<AlertListFragment> {
    private final Provider<AlertListAdapter> alertListAdapterProvider;
    private final Provider<LinearLayoutManager> mLayoutManagerProvider;
    private final Provider<AlertListMvpPresenter<AlertListMvpView>> mPresenterProvider;

    public AlertListFragment_MembersInjector(Provider<AlertListMvpPresenter<AlertListMvpView>> provider, Provider<AlertListAdapter> provider2, Provider<LinearLayoutManager> provider3) {
        this.mPresenterProvider = provider;
        this.alertListAdapterProvider = provider2;
        this.mLayoutManagerProvider = provider3;
    }

    public static MembersInjector<AlertListFragment> create(Provider<AlertListMvpPresenter<AlertListMvpView>> provider, Provider<AlertListAdapter> provider2, Provider<LinearLayoutManager> provider3) {
        return new AlertListFragment_MembersInjector(provider, provider2, provider3);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(AlertListFragment alertListFragment) {
        injectMPresenter(alertListFragment, this.mPresenterProvider.get());
        injectAlertListAdapter(alertListFragment, this.alertListAdapterProvider.get());
        injectMLayoutManager(alertListFragment, this.mLayoutManagerProvider.get());
    }

    public static void injectMPresenter(AlertListFragment alertListFragment, AlertListMvpPresenter<AlertListMvpView> alertListMvpPresenter) {
        alertListFragment.mPresenter = alertListMvpPresenter;
    }

    public static void injectAlertListAdapter(AlertListFragment alertListFragment, AlertListAdapter alertListAdapter) {
        alertListFragment.alertListAdapter = alertListAdapter;
    }

    public static void injectMLayoutManager(AlertListFragment alertListFragment, LinearLayoutManager linearLayoutManager) {
        alertListFragment.mLayoutManager = linearLayoutManager;
    }
}
