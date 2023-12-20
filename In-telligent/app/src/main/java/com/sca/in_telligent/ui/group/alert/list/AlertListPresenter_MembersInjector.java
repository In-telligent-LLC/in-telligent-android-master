package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AlertListPresenter_MembersInjector<V extends AlertListMvpView> implements MembersInjector<AlertListPresenter<V>> {
    private final Provider<TwilioUtil> twilioUtilProvider;



    public AlertListPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
    }

    public static <V extends AlertListMvpView> MembersInjector<AlertListPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new AlertListPresenter_MembersInjector(provider);
    }

    public void injectMembers(AlertListPresenter<V> alertListPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(alertListPresenter, this.twilioUtilProvider.get());
    }
}
