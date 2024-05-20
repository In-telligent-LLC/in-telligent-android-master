package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.twilio.AppTwilioUtil;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideTwilioUtilFactory implements Factory<TwilioUtil> {
    private final Provider<AppTwilioUtil> appTwilioUtilProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideTwilioUtilFactory(ApplicationModule applicationModule, Provider<AppTwilioUtil> provider) {
        this.module = applicationModule;
        this.appTwilioUtilProvider = provider;
    }

    @Override // javax.inject.Provider
    public TwilioUtil get() {
        return provideTwilioUtil(this.module, this.appTwilioUtilProvider.get());
    }

    public static ApplicationModule_ProvideTwilioUtilFactory create(ApplicationModule applicationModule, Provider<AppTwilioUtil> provider) {
        return new ApplicationModule_ProvideTwilioUtilFactory(applicationModule, provider);
    }

    public static TwilioUtil provideTwilioUtil(ApplicationModule applicationModule, AppTwilioUtil appTwilioUtil) {
        return (TwilioUtil) Preconditions.checkNotNullFromProvides(applicationModule.provideTwilioUtil(appTwilioUtil));
    }
}
