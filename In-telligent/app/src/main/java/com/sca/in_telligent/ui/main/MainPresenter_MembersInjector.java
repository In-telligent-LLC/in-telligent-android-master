package com.sca.in_telligent.ui.main;

import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.main.MainMvpView;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class MainPresenter_MembersInjector<V extends MainMvpView> implements MembersInjector<MainPresenter<V>> {
//    private final Provider<RxPermissions> rxPermissionsProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

//    @Override // dagger.MembersInjector
//    public /* bridge */ /* synthetic */ void injectMembers(Object obj) {
//        injectMembers((MainPresenter) ((MainPresenter) obj));
//    }

    public MainPresenter_MembersInjector(Provider<TwilioUtil> provider) {
        this.twilioUtilProvider = provider;
//        this.rxPermissionsProvider = provider2;
    }

    public static <V extends MainMvpView> MembersInjector<MainPresenter<V>> create(Provider<TwilioUtil> provider) {
        return new MainPresenter_MembersInjector(provider);
    }

    public void injectMembers(MainPresenter<V> mainPresenter) {
        BasePresenter_MembersInjector.injectTwilioUtil(mainPresenter, this.twilioUtilProvider.get());
//        BasePresenter_MembersInjector.injectRxPermissions(mainPresenter);
    }
}
