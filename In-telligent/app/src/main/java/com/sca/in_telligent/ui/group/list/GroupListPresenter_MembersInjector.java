package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.group.list.GroupListMvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GroupListPresenter_MembersInjector<V extends GroupListMvpView> implements MembersInjector<GroupListPresenter<V>> {
    private final Provider<TwilioUtil> twilioUtilProvider;


    public GroupListPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
    }

    public static <V extends GroupListMvpView> MembersInjector<GroupListPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new GroupListPresenter_MembersInjector(provider);
    }

    public void injectMembers(GroupListPresenter<V> groupListPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(groupListPresenter, this.twilioUtilProvider.get());
    }
}
