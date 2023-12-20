package com.sca.in_telligent.di.component;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;

import androidx.work.WorkManager;

import com.google.android.gms.location.GeofencingClient;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.ScaApplication_MembersInjector;
import com.sca.in_telligent.data.AppDataManager;
import com.sca.in_telligent.data.AppDataManager_Factory;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.prefs.AppPreferencesHelper;
import com.sca.in_telligent.data.prefs.AppPreferencesHelper_Factory;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.di.module.ActivityModule;
import com.sca.in_telligent.di.module.ApplicationModule;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideApiHelperFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideApplicationFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideAudioHelperFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideAudioManagerFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideCompositeDisposableFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideContextFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideDataManagerFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideFlashHelperFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideGeofenceClientFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideGeofencingClientFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideLifecycleObserverFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideLocationUtilFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvidePreferenceNameFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvidePreferencesHelperFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideResponderFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideSchedulerProviderFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideTwilioUtilFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideVibratorFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideVideoDownloaderFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideWeatherUtilFactory;
import com.sca.in_telligent.di.module.ApplicationModule_ProvideWorkManagerFactory;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.openapi.util.FlashHelper;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService_MembersInjector;
import com.sca.in_telligent.util.AppLifecycleObserver;
import com.sca.in_telligent.util.AppLifecycleObserver_Factory;
import com.sca.in_telligent.util.AppLocationUtil;
import com.sca.in_telligent.util.AppLocationUtil_Factory;
import com.sca.in_telligent.util.AppResponder;
import com.sca.in_telligent.util.AppResponder_Factory;
import com.sca.in_telligent.util.AppVideoDownloader;
import com.sca.in_telligent.util.AppVideoDownloader_Factory;
import com.sca.in_telligent.util.AppWeatherUtil;
import com.sca.in_telligent.util.AppWeatherUtil_Factory;
import com.sca.in_telligent.util.LifecycleInterface;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.AppGeofenceClient;
import com.sca.in_telligent.util.geofence.AppGeofenceClient_Factory;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.AppTwilioUtil;
import com.sca.in_telligent.util.twilio.AppTwilioUtil_Factory;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;

import javax.inject.Provider;

public final class DaggerActivityComponent implements ApplicationComponent {
    private Provider<AppDataManager> appDataManagerProvider;
    private Provider<AppGeofenceClient> appGeofenceClientProvider;
    private Provider<AppLifecycleObserver> appLifecycleObserverProvider;
    private Provider<AppLocationUtil> appLocationUtilProvider;
    private Provider<AppPreferencesHelper> appPreferencesHelperProvider;
    private Provider<AppResponder> appResponderProvider;
    private Provider<AppTwilioUtil> appTwilioUtilProvider;
    private Provider<AppVideoDownloader> appVideoDownloaderProvider;
    private Provider<AppWeatherUtil> appWeatherUtilProvider;
    private final DaggerActivityComponent applicationComponent;
    private final ApplicationModule applicationModule;
    private Provider<ApiHelper> provideApiHelperProvider;
    private Provider<AudioHelper> provideAudioHelperProvider;
    private Provider<AudioManager> provideAudioManagerProvider;
    private ApplicationModule_ProvideCompositeDisposableFactory provideCompositeDisposableProvider;
    private Provider<Context> provideContextProvider;
    private Provider<DataManager> provideDataManagerProvider;
    private Provider<WorkManager> provideWorkManagerProvider;
    private Provider<FlashHelper> provideFlashHelperProvider;
    private Provider<GeofenceClient> provideGeofenceClientProvider;
    private Provider<GeofencingClient> provideGeofencingClientProvider;
    private Provider<LifecycleInterface> provideLifecycleObserverProvider;
    private Provider<LocationUtil> provideLocationUtilProvider;
    private Provider<String> providePreferenceNameProvider;
    private Provider<PreferencesHelper> providePreferencesHelperProvider;
    private Provider<Responder> provideResponderProvider;
    private Provider<SchedulerProvider> provideSchedulerProvider;
    private Provider<TwilioUtil> provideTwilioUtilProvider;
    private Provider<Vibrator> provideVibratorProvider;
    private Provider<VideoDownloader> provideVideoDownloaderProvider;
    private Provider<WeatherUtil> provideWeatherUtilProvider;

    private DaggerActivityComponent(ApplicationModule applicationModule) {
        this.applicationComponent = this;
        this.applicationModule = applicationModule;
        initialize(applicationModule);
    }

    public static Builder builder() {
        return new Builder();
    }


    private void initialize(ApplicationModule applicationModule) {
        this.provideContextProvider = ApplicationModule_ProvideContextFactory.create(applicationModule);
        ApplicationModule_ProvidePreferenceNameFactory create = ApplicationModule_ProvidePreferenceNameFactory.create(applicationModule);
        this.providePreferenceNameProvider = create;
        Provider<AppPreferencesHelper> provider = DoubleCheck.provider(AppPreferencesHelper_Factory.create(this.provideContextProvider, create));
        this.appPreferencesHelperProvider = provider;
        this.providePreferencesHelperProvider = DoubleCheck.provider(ApplicationModule_ProvidePreferencesHelperFactory.create(applicationModule, provider));
        Provider<ApiHelper> provider2 = DoubleCheck.provider(ApplicationModule_ProvideApiHelperFactory.create(applicationModule));
        this.provideApiHelperProvider = provider2;
        Provider<AppDataManager> provider3 = DoubleCheck.provider(AppDataManager_Factory.create(this.provideContextProvider, this.providePreferencesHelperProvider, provider2));
        this.appDataManagerProvider = provider3;
        this.provideDataManagerProvider = DoubleCheck.provider(ApplicationModule_ProvideDataManagerFactory.create(applicationModule, provider3));
        this.provideSchedulerProvider = ApplicationModule_ProvideSchedulerProviderFactory.create(applicationModule);
        ApplicationModule_ProvideCompositeDisposableFactory create2 = ApplicationModule_ProvideCompositeDisposableFactory.create(applicationModule);
        this.provideCompositeDisposableProvider = create2;
        AppLifecycleObserver_Factory create3 = AppLifecycleObserver_Factory.create(this.provideDataManagerProvider, this.provideSchedulerProvider, create2);
        this.appLifecycleObserverProvider = create3;
        this.provideLifecycleObserverProvider = DoubleCheck.provider(ApplicationModule_ProvideLifecycleObserverFactory.create(applicationModule, create3));
        this.provideAudioManagerProvider = DoubleCheck.provider(ApplicationModule_ProvideAudioManagerFactory.create(applicationModule, this.provideContextProvider));
        Provider<AppLocationUtil> provider4 = DoubleCheck.provider(AppLocationUtil_Factory.create(this.provideContextProvider));
        this.appLocationUtilProvider = provider4;
        this.provideLocationUtilProvider = DoubleCheck.provider(ApplicationModule_ProvideLocationUtilFactory.create(applicationModule, provider4));
        Provider<GeofencingClient> provider5 = DoubleCheck.provider(ApplicationModule_ProvideGeofencingClientFactory.create(applicationModule, this.provideContextProvider));
        this.provideGeofencingClientProvider = provider5;
        Provider<AppGeofenceClient> provider6 = DoubleCheck.provider(AppGeofenceClient_Factory.create(provider5, this.provideLocationUtilProvider, this.provideDataManagerProvider, this.provideSchedulerProvider, this.provideCompositeDisposableProvider, this.provideContextProvider));
        this.appGeofenceClientProvider = provider6;
        this.provideGeofenceClientProvider = DoubleCheck.provider(ApplicationModule_ProvideGeofenceClientFactory.create(applicationModule, provider6));
        Provider<AppWeatherUtil> provider7 = DoubleCheck.provider(AppWeatherUtil_Factory.create(this.provideContextProvider, this.provideLocationUtilProvider, this.provideDataManagerProvider));
        this.appWeatherUtilProvider = provider7;
        this.provideWeatherUtilProvider = DoubleCheck.provider(ApplicationModule_ProvideWeatherUtilFactory.create(applicationModule, provider7));
        this.provideVibratorProvider = DoubleCheck.provider(ApplicationModule_ProvideVibratorFactory.create(applicationModule, this.provideContextProvider));
        Provider<FlashHelper> provider8 = DoubleCheck.provider(ApplicationModule_ProvideFlashHelperFactory.create(applicationModule, this.provideContextProvider));
        this.provideFlashHelperProvider = provider8;
        this.provideAudioHelperProvider = DoubleCheck.provider(ApplicationModule_ProvideAudioHelperFactory.create(applicationModule, this.provideContextProvider, this.provideAudioManagerProvider, this.provideVibratorProvider, provider8));
        Provider<AppResponder> provider9 = DoubleCheck.provider(AppResponder_Factory.create(this.provideDataManagerProvider));
        this.appResponderProvider = provider9;
        Provider<Responder> provider10 = DoubleCheck.provider(ApplicationModule_ProvideResponderFactory.create(applicationModule, provider9));
        this.provideResponderProvider = provider10;
        Provider<AppTwilioUtil> provider11 = DoubleCheck.provider(AppTwilioUtil_Factory.create(this.provideContextProvider, this.provideDataManagerProvider, this.provideSchedulerProvider, this.provideCompositeDisposableProvider, provider10, this.providePreferencesHelperProvider));
        this.appTwilioUtilProvider = provider11;
        this.provideTwilioUtilProvider = DoubleCheck.provider(ApplicationModule_ProvideTwilioUtilFactory.create(applicationModule, provider11));
        Provider<AppVideoDownloader> provider12 = DoubleCheck.provider(AppVideoDownloader_Factory.create(this.provideContextProvider));
        this.appVideoDownloaderProvider = provider12;
        this.provideVideoDownloaderProvider = DoubleCheck.provider(ApplicationModule_ProvideVideoDownloaderFactory.create(applicationModule, provider12));
        this.provideWorkManagerProvider = DoubleCheck.provider(ApplicationModule_ProvideWorkManagerFactory.create(applicationModule, this.provideContextProvider));
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public void inject(ScaApplication scaApplication) {
        injectScaApplication(scaApplication);
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public void inject(GeofenceTransitionsIntentService geofenceTransitionsIntentService) {
        injectGeofenceTransitionsIntentService(geofenceTransitionsIntentService);
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public Context context() {
        return ApplicationModule_ProvideContextFactory.provideContext(this.applicationModule);
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public Application application() {
        return ApplicationModule_ProvideApplicationFactory.provideApplication(this.applicationModule);
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public DataManager getDataManager() {
        return this.provideDataManagerProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public AudioManager getAudioManager() {
        return this.provideAudioManagerProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public AudioHelper getAudioHelper() {
        return this.provideAudioHelperProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public WeatherUtil getWeatherUtil() {
        return this.provideWeatherUtilProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public LocationUtil getLocationUtil() {
        return this.provideLocationUtilProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public Responder getResponder() {
        return this.provideResponderProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public GeofenceClient getGeofenceClient() {
        return this.provideGeofenceClientProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public TwilioUtil getTwilioUtil() {
        return this.provideTwilioUtilProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public LifecycleInterface getLifecycleInterface() {
        return this.provideLifecycleObserverProvider.get();
    }

    @Override // com.sca.in_telligent.di.component.ApplicationComponent
    public VideoDownloader getVideoDownloader() {
        return this.provideVideoDownloaderProvider.get();
    }

    @Override
    public WorkManager getWorkManager() {
        return this.provideWorkManagerProvider.get();
    }

    private ScaApplication injectScaApplication(ScaApplication scaApplication) {
        ScaApplication_MembersInjector.injectMDataManager(scaApplication, this.provideDataManagerProvider.get());
        ScaApplication_MembersInjector.injectAppLifecycleObserver(scaApplication, this.provideLifecycleObserverProvider.get());
        ScaApplication_MembersInjector.injectAudioManager(scaApplication, this.provideAudioManagerProvider.get());
        return scaApplication;
    }

    private GeofenceTransitionsIntentService injectGeofenceTransitionsIntentService(GeofenceTransitionsIntentService geofenceTransitionsIntentService) {
        GeofenceTransitionsIntentService_MembersInjector.injectMLocationUtil(geofenceTransitionsIntentService, this.provideLocationUtilProvider.get());
        GeofenceTransitionsIntentService_MembersInjector.injectMDataManager(geofenceTransitionsIntentService, this.provideDataManagerProvider.get());
        GeofenceTransitionsIntentService_MembersInjector.injectMSchedulerProvider(geofenceTransitionsIntentService, ApplicationModule_ProvideSchedulerProviderFactory.provideSchedulerProvider(this.applicationModule));
        GeofenceTransitionsIntentService_MembersInjector.injectMCompositeDisposable(geofenceTransitionsIntentService, ApplicationModule_ProvideCompositeDisposableFactory.provideCompositeDisposable(this.applicationModule));
        GeofenceTransitionsIntentService_MembersInjector.injectMGeofenceClient(geofenceTransitionsIntentService, this.provideGeofenceClientProvider.get());
        GeofenceTransitionsIntentService_MembersInjector.injectMWeatherUtil(geofenceTransitionsIntentService, this.provideWeatherUtilProvider.get());
        return geofenceTransitionsIntentService;
    }

    public static final class Builder {
        private ApplicationModule applicationModule;
        private ApplicationComponent applicationComponent;
        private ActivityModule activityModule;

        private Builder() {
        }

        public Builder applicationModule(ApplicationModule applicationModule) {
            this.applicationModule = (ApplicationModule) Preconditions.checkNotNull(applicationModule);
            return this;
        }

        public Builder activityModule(ApplicationComponent applicationComponent) {
            this.activityModule = (ActivityModule) Preconditions.checkNotNull(applicationComponent);
            return this;
        }


        public ApplicationComponent build() {
            Preconditions.checkBuilderRequirement(this.applicationModule, ApplicationModule.class);
            return new DaggerActivityComponent(this.applicationModule);
        }

        public DaggerActivityComponent.Builder activityModule(ActivityModule activityModule) {
            return null;
        }

        public Builder applicationComponent(ApplicationComponent component) {
            return null;
        }
    }
}
