package com.sca.in_telligent.di.component;

import com.sca.in_telligent.di.PerService;
import com.sca.in_telligent.di.module.ServiceModule;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService;
import com.sca.in_telligent.service.GeofencesUpdateWorker;
import com.sca.in_telligent.service.HeadsUpNotificationService;
import com.sca.in_telligent.service.MyFirebaseMessagingService;
import com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(HeadsUpNotificationService headsUpNotificationService);

    void inject(MyFirebaseMessagingService service);

  void inject(GeofenceTransitionsIntentService service);

  void inject(GeofencesUpdateWorker service);

    void inject(VoiceCallNotificationActivity voiceCallNotificationActivity);
}
