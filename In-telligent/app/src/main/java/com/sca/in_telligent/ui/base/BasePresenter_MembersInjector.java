package com.sca.in_telligent.ui.base;

import com.sca.in_telligent.ui.base.MvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class BasePresenter_MembersInjector<V extends MvpView> implements MembersInjector<BasePresenter<V>> {
    private final Provider<RxPermissions> rxPermissionsProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

    @Override // dagger.MembersInjector
    public /* bridge */ /* synthetic */ void injectMembers(Object obj) {
        injectMembers((BasePresenter) ((BasePresenter) obj));
    }

    public BasePresenter_MembersInjector(Provider<TwilioUtil> provider, Provider<RxPermissions> provider2) {
        this.twilioUtilProvider = provider;
        this.rxPermissionsProvider = provider2;
    }

    public static <V extends MvpView> MembersInjector<BasePresenter<V>> create(Provider<TwilioUtil> provider, Provider<RxPermissions> provider2) {
        return new BasePresenter_MembersInjector(provider, provider2);
    }

    public void injectMembers(BasePresenter<V> basePresenter) {
        injectTwilioUtil(basePresenter, this.twilioUtilProvider.get());
        injectRxPermissions(basePresenter, this.rxPermissionsProvider.get());
    }

    public static <V extends MvpView> void injectTwilioUtil(BasePresenter<V> basePresenter, TwilioUtil twilioUtil) {
        basePresenter.twilioUtil = twilioUtil;
    }

    public static <V extends MvpView> void injectRxPermissions(BasePresenter<V> basePresenter, RxPermissions rxPermissions) {
        basePresenter.rxPermissions = rxPermissions;
    }
}
