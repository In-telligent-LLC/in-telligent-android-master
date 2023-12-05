package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpPresenter;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideGroupDetailPresenterFactory implements Factory<GroupDetailMvpPresenter<GroupDetailMvpView>> {
    private final ActivityModule module;
    private final Provider<GroupDetailPresenter<GroupDetailMvpView>> presenterProvider;

    public ActivityModule_ProvideGroupDetailPresenterFactory(ActivityModule activityModule, Provider<GroupDetailPresenter<GroupDetailMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public GroupDetailMvpPresenter<GroupDetailMvpView> get() {
        return provideGroupDetailPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideGroupDetailPresenterFactory create(ActivityModule activityModule, Provider<GroupDetailPresenter<GroupDetailMvpView>> provider) {
        return new ActivityModule_ProvideGroupDetailPresenterFactory(activityModule, provider);
    }

    public static GroupDetailMvpPresenter<GroupDetailMvpView> provideGroupDetailPresenter(ActivityModule activityModule, GroupDetailPresenter<GroupDetailMvpView> groupDetailPresenter) {
        return (GroupDetailMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideGroupDetailPresenter(groupDetailPresenter));
    }
}
