package com.sca.in_telligent.util;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AppLocationUtil_Factory implements Factory<AppLocationUtil> {
    private final Provider<Context> contextProvider;

    public AppLocationUtil_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public AppLocationUtil get() {
        return newInstance(this.contextProvider.get());
    }

    public static AppLocationUtil_Factory create(Provider<Context> provider) {
        return new AppLocationUtil_Factory(provider);
    }

    public static AppLocationUtil newInstance(Context context) {
        return new AppLocationUtil(context);
    }
}
