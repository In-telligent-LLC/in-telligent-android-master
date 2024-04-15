package com.sca.in_telligent.di.component;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;

import androidx.work.WorkManager;

import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.di.module.ApplicationModule;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService;
import com.sca.in_telligent.service.MyFirebaseMessagingService;
import com.sca.in_telligent.util.LifecycleInterface;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.twilio.TwilioUtil;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(ScaApplication app);

  void inject(GeofenceTransitionsIntentService service);

  void inject (MyFirebaseMessagingService service);

  @ApplicationContext
  Context context();

  Application application();

  DataManager getDataManager();

  AudioManager getAudioManager();

  AudioHelper getAudioHelper();

  WeatherUtil getWeatherUtil();

  LocationUtil getLocationUtil();

  Responder getResponder();

  GeofenceClient getGeofenceClient();

  TwilioUtil getTwilioUtil();

  LifecycleInterface getLifecycleInterface();

  VideoDownloader getVideoDownloader();

  WorkManager getWorkManager();


}