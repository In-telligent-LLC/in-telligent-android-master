package com.sca.in_telligent.data.prefs;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppPreferencesHelper_Factory implements Factory<AppPreferencesHelper> {
    private final Provider<Context> contextProvider;
    private final Provider<String> prefFileNameProvider;

    public AppPreferencesHelper_Factory(Provider<Context> provider, Provider<String> provider2) {
        this.contextProvider = provider;
        this.prefFileNameProvider = provider2;
    }

    @Override // javax.inject.Provider
    public AppPreferencesHelper get() {
        return newInstance(this.contextProvider.get(), this.prefFileNameProvider.get());
    }

    public static AppPreferencesHelper_Factory create(Provider<Context> provider, Provider<String> provider2) {
        return new AppPreferencesHelper_Factory(provider, provider2);
    }

    public static AppPreferencesHelper newInstance(Context context, String str) {
        return new AppPreferencesHelper(context, str);
    }
}
