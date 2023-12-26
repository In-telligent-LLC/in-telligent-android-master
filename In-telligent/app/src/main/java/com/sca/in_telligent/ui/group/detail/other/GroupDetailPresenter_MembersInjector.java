package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GroupDetailPresenter_MembersInjector<V extends GroupDetailMvpView> implements MembersInjector<GroupDetailPresenter<V>> {
    private final Provider<TwilioUtil> twilioUtilProvider;

    public GroupDetailPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
    }

    public static <V extends GroupDetailMvpView> MembersInjector<GroupDetailPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new GroupDetailPresenter_MembersInjector(provider);
    }

    public void injectMembers(GroupDetailPresenter<V> groupDetailPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(groupDetailPresenter, this.twilioUtilProvider.get());
    }
}
