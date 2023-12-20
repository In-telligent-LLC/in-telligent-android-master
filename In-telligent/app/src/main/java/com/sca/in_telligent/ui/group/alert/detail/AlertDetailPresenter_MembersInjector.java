package com.sca.in_telligent.ui.group.alert.detail;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AlertDetailPresenter_MembersInjector<V extends AlertDetailMvpView> implements MembersInjector<AlertDetailPresenter<V>> {
    private final Provider<TwilioUtil> twilioUtilProvider;


    public AlertDetailPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
    }

    public static <V extends AlertDetailMvpView> MembersInjector<AlertDetailPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new AlertDetailPresenter_MembersInjector(provider);
    }

    public void injectMembers(AlertDetailPresenter<V> alertDetailPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(alertDetailPresenter, this.twilioUtilProvider.get());
    }
}
