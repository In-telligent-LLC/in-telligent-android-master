package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.AppWeatherUtil;
import com.sca.in_telligent.util.WeatherUtil;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideWeatherUtilFactory implements Factory<WeatherUtil> {
    private final Provider<AppWeatherUtil> appWeatherUtilProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideWeatherUtilFactory(ApplicationModule applicationModule, Provider<AppWeatherUtil> provider) {
        this.module = applicationModule;
        this.appWeatherUtilProvider = provider;
    }

    @Override // javax.inject.Provider
    public WeatherUtil get() {
        return provideWeatherUtil(this.module, this.appWeatherUtilProvider.get());
    }

    public static ApplicationModule_ProvideWeatherUtilFactory create(ApplicationModule applicationModule, Provider<AppWeatherUtil> provider) {
        return new ApplicationModule_ProvideWeatherUtilFactory(applicationModule, provider);
    }

    public static WeatherUtil provideWeatherUtil(ApplicationModule applicationModule, AppWeatherUtil appWeatherUtil) {
        return (WeatherUtil) Preconditions.checkNotNullFromProvides(applicationModule.provideWeatherUtil(appWeatherUtil));
    }
}
