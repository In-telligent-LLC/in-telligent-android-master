package com.sca.in_telligent.ui.group.detail.other;

import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class GroupDetailFragment_MembersInjector implements MembersInjector<GroupDetailFragment> {
    private final Provider<GroupDetailMvpPresenter<GroupDetailMvpView>> mPresenterProvider;

    public GroupDetailFragment_MembersInjector(Provider<GroupDetailMvpPresenter<GroupDetailMvpView>> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<GroupDetailFragment> create(Provider<GroupDetailMvpPresenter<GroupDetailMvpView>> provider) {
        return new GroupDetailFragment_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(GroupDetailFragment groupDetailFragment) {
        injectMPresenter(groupDetailFragment, this.mPresenterProvider.get());
    }

    public static void injectMPresenter(GroupDetailFragment groupDetailFragment, GroupDetailMvpPresenter<GroupDetailMvpView> groupDetailMvpPresenter) {
        groupDetailFragment.mPresenter = groupDetailMvpPresenter;
    }
}
