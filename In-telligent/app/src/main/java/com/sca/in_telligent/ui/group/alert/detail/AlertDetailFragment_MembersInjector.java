package com.sca.in_telligent.ui.group.alert.detail;

import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AlertDetailFragment_MembersInjector implements MembersInjector<AlertDetailFragment> {
    private final Provider<AlertDetailMvpPresenter<AlertDetailMvpView>> mPresenterProvider;

    public AlertDetailFragment_MembersInjector(Provider<AlertDetailMvpPresenter<AlertDetailMvpView>> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<AlertDetailFragment> create(Provider<AlertDetailMvpPresenter<AlertDetailMvpView>> provider) {
        return new AlertDetailFragment_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(AlertDetailFragment alertDetailFragment) {
        injectMPresenter(alertDetailFragment, this.mPresenterProvider.get());
    }

    public static void injectMPresenter(AlertDetailFragment alertDetailFragment, AlertDetailMvpPresenter<AlertDetailMvpView> alertDetailMvpPresenter) {
        alertDetailFragment.mPresenter = alertDetailMvpPresenter;
    }
}
