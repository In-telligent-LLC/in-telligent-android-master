package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class NotificationDetailPresenter_MembersInjector<V extends NotificationDetailMvpView> implements MembersInjector<NotificationDetailPresenter<V>> {
//    private final Provider<RxPermissions> rxPermissionsProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;


    public NotificationDetailPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
    }

    public static <V extends NotificationDetailMvpView> MembersInjector<NotificationDetailPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new NotificationDetailPresenter_MembersInjector(provider);
    }

    public void injectMembers(NotificationDetailPresenter<V> notificationDetailPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(notificationDetailPresenter, this.twilioUtilProvider.get());
    }
}
