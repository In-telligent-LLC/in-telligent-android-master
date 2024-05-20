package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailMvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class NotificationDetailPresenter_MembersInjector<V extends NotificationDetailMvpView> implements MembersInjector<NotificationDetailPresenter<V>> {
    private final Provider<RxPermissions> rxPermissionsProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

    @Override // dagger.MembersInjector
    public /* bridge */ /* synthetic */ void injectMembers(Object obj) {
        injectMembers((NotificationDetailPresenter) ((NotificationDetailPresenter) obj));
    }

    public NotificationDetailPresenter_MembersInjector(Provider<TwilioUtil> provider, Provider<RxPermissions> provider2) {
        this.twilioUtilProvider = provider;
        this.rxPermissionsProvider = provider2;
    }

    public static <V extends NotificationDetailMvpView> MembersInjector<NotificationDetailPresenter<V>> create(Provider<TwilioUtil> provider, Provider<RxPermissions> provider2) {
        return new NotificationDetailPresenter_MembersInjector(provider, provider2);
    }

    public void injectMembers(NotificationDetailPresenter<V> notificationDetailPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(notificationDetailPresenter, this.twilioUtilProvider.get());
        BasePresenter_MembersInjector.injectRxPermissions(notificationDetailPresenter, this.rxPermissionsProvider.get());
    }
}
