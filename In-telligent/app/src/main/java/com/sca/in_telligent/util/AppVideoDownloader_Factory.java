package com.sca.in_telligent.util;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppVideoDownloader_Factory implements Factory<AppVideoDownloader> {
    private final Provider<Context> contextProvider;

    public AppVideoDownloader_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public AppVideoDownloader get() {
        return newInstance(this.contextProvider.get());
    }

    public static AppVideoDownloader_Factory create(Provider<Context> provider) {
        return new AppVideoDownloader_Factory(provider);
    }

    public static AppVideoDownloader newInstance(Context context) {
        return new AppVideoDownloader(context);
    }
}
