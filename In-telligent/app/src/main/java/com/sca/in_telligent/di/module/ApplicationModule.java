package com.sca.in_telligent.di.module;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.sca.in_telligent.data.AppDataManager;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.prefs.AppPreferencesHelper;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.di.PreferenceInfo;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;
import com.sca.in_telligent.openapi.util.OpenApiAudioHelper;
import com.sca.in_telligent.openapi.util.OpenApiFlashHelper;
import com.sca.in_telligent.util.AppLifecycleObserver;
import com.sca.in_telligent.util.AppLocationUtil;
import com.sca.in_telligent.util.AppResponder;
import com.sca.in_telligent.util.AppVideoDownloader;
import com.sca.in_telligent.util.AppWeatherUtil;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.openapi.util.FlashHelper;
import com.sca.in_telligent.util.LifecycleInterface;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.AppGeofenceClient;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.rx.AppSchedulerProvider;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.AppTwilioUtil;
import com.sca.in_telligent.util.twilio.TwilioUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    VideoDownloader provideVideoDownloader(AppVideoDownloader appVideoDownloader) {
        return appVideoDownloader;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return OpenApiPreferencesHelperImpl.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    LifecycleInterface provideLifecycleObserver(AppLifecycleObserver appLifecycleObserver) {
        return appLifecycleObserver;
    }

    @Provides
    @Singleton
    GeofenceClient provideGeofenceClient(AppGeofenceClient appGeofenceClient) {
        return appGeofenceClient;
    }

    @Provides
    @Singleton
    TwilioUtil provideTwilioUtil(AppTwilioUtil appTwilioUtil) {
        return appTwilioUtil;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager(@ApplicationContext Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    @Provides
    @Singleton
    ApiHelper provideApiHelper() {
        return OpenAPI.getApiHelper();
    }

    @Provides
    @Singleton
    GeofencingClient provideGeofencingClient(@ApplicationContext Context context) {
        return LocationServices.getGeofencingClient(context);
    }

    @Provides
    @Singleton
    Vibrator provideVibrator(@ApplicationContext Context context) {
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Provides
    @Singleton
    LocationUtil provideLocationUtil(AppLocationUtil appLocationUtil) {
        return appLocationUtil;
    }

    @Provides
    @Singleton
    WeatherUtil provideWeatherUtil(AppWeatherUtil appWeatherUtil) {
        return appWeatherUtil;
    }

    @Provides
    @Singleton
    FlashHelper provideFlashHelper(@ApplicationContext Context context) {
        return OpenApiFlashHelper.newInstance(context);
    }

    @Provides
    @Singleton
    AudioHelper provideAudioHelper(@ApplicationContext Context context, AudioManager audioManager, Vibrator vibrator, FlashHelper flashHelper) {
        return OpenApiAudioHelper.newInstance(context, audioManager, vibrator, flashHelper);
    }

    @Provides
    @Singleton
    Responder provideResponder(AppResponder appResponder) {
        return appResponder;
    }

    @Provides
    @Singleton
    FirebaseJobDispatcher provideFirebaseJobDispatcher(
            @ApplicationContext Context context) {
        return new FirebaseJobDispatcher(new GooglePlayDriver(context));
    }
}
