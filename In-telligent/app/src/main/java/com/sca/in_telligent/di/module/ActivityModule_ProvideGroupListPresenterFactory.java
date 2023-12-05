package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.group.list.GroupListMvpPresenter;
import com.sca.in_telligent.ui.group.list.GroupListMvpView;
import com.sca.in_telligent.ui.group.list.GroupListPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideGroupListPresenterFactory implements Factory<GroupListMvpPresenter<GroupListMvpView>> {
    private final ActivityModule module;
    private final Provider<GroupListPresenter<GroupListMvpView>> presenterProvider;

    public ActivityModule_ProvideGroupListPresenterFactory(ActivityModule activityModule, Provider<GroupListPresenter<GroupListMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public GroupListMvpPresenter<GroupListMvpView> get() {
        return provideGroupListPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideGroupListPresenterFactory create(ActivityModule activityModule, Provider<GroupListPresenter<GroupListMvpView>> provider) {
        return new ActivityModule_ProvideGroupListPresenterFactory(activityModule, provider);
    }

    public static GroupListMvpPresenter<GroupListMvpView> provideGroupListPresenter(ActivityModule activityModule, GroupListPresenter<GroupListMvpView> groupListPresenter) {
        return (GroupListMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideGroupListPresenter(groupListPresenter));
    }
}
