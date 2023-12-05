package com.sca.in_telligent.di.component;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.module.ServiceModule;
import com.sca.in_telligent.di.module.ServiceModule_ProvideCompositeDisposableFactory;
import com.sca.in_telligent.di.module.ServiceModule_ProvideSchedulerProviderFactory;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService;
import com.sca.in_telligent.service.GeofenceTransitionsIntentService_MembersInjector;
import com.sca.in_telligent.service.GeofencesUpdateService;
import com.sca.in_telligent.service.GeofencesUpdateService_MembersInjector;
import com.sca.in_telligent.service.HeadsUpNotificationService;
import com.sca.in_telligent.service.MyFirebaseMessagingService;
import com.sca.in_telligent.service.MyFirebaseMessagingService_MembersInjector;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import dagger.internal.Preconditions;

public final class DaggerServiceComponent implements ServiceComponent {
    private final ApplicationComponent applicationComponent;
    private final DaggerServiceComponent serviceComponent;
    private final ServiceModule serviceModule;

    @Override
    public void inject(HeadsUpNotificationService headsUpNotificationService) {
    }

    private DaggerServiceComponent(ServiceModule serviceModule, ApplicationComponent applicationComponent) {
        this.serviceComponent = this;
        this.applicationComponent = applicationComponent;
        this.serviceModule = serviceModule;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override // com.sca.in_telligent.di.component.ServiceComponent
    public void inject(MyFirebaseMessagingService myFirebaseMessagingService) {
        injectMyFirebaseMessagingService(myFirebaseMessagingService);
    }

    @Override
    public void inject(GeofenceTransitionsIntentService geofenceTransitionsIntentService) {
        injectGeofenceTransitionsIntentService(geofenceTransitionsIntentService);
    }

    @Override
    public void inject(GeofencesUpdateService geofencesUpdateService) {
        injectGeofencesUpdateService(geofencesUpdateService);
    }

    private MyFirebaseMessagingService injectMyFirebaseMessagingService(MyFirebaseMessagingService myFirebaseMessagingService) {
        MyFirebaseMessagingService_MembersInjector.injectDataManager(myFirebaseMessagingService, (DataManager) Preconditions.checkNotNull(this.applicationComponent.getDataManager()));
        MyFirebaseMessagingService_MembersInjector.injectAudioHelper(myFirebaseMessagingService, (AudioHelper) Preconditions.checkNotNull(this.applicationComponent.getAudioHelper()));
        MyFirebaseMessagingService_MembersInjector.injectWeatherUtil(myFirebaseMessagingService, (WeatherUtil) Preconditions.checkNotNull(this.applicationComponent.getWeatherUtil()));
        MyFirebaseMessagingService_MembersInjector.injectResponder(myFirebaseMessagingService, (Responder) Preconditions.checkNotNull(this.applicationComponent.getResponder()));
        MyFirebaseMessagingService_MembersInjector.injectLocationUtil(myFirebaseMessagingService, (LocationUtil) Preconditions.checkNotNull(this.applicationComponent.getLocationUtil()));
        MyFirebaseMessagingService_MembersInjector.injectGeofenceClient(myFirebaseMessagingService, (GeofenceClient) Preconditions.checkNotNull(this.applicationComponent.getGeofenceClient()));
        return myFirebaseMessagingService;
    }

    private GeofenceTransitionsIntentService injectGeofenceTransitionsIntentService(GeofenceTransitionsIntentService geofenceTransitionsIntentService) {
        GeofenceTransitionsIntentService_MembersInjector.injectMLocationUtil(geofenceTransitionsIntentService, (LocationUtil) Preconditions.checkNotNull(this.applicationComponent.getLocationUtil()));
        GeofenceTransitionsIntentService_MembersInjector.injectMDataManager(geofenceTransitionsIntentService, (DataManager) Preconditions.checkNotNull(this.applicationComponent.getDataManager()));
        GeofenceTransitionsIntentService_MembersInjector.injectMSchedulerProvider(geofenceTransitionsIntentService, ServiceModule_ProvideSchedulerProviderFactory.provideSchedulerProvider(this.serviceModule));
        GeofenceTransitionsIntentService_MembersInjector.injectMCompositeDisposable(geofenceTransitionsIntentService, ServiceModule_ProvideCompositeDisposableFactory.provideCompositeDisposable(this.serviceModule));
        GeofenceTransitionsIntentService_MembersInjector.injectMGeofenceClient(geofenceTransitionsIntentService, (GeofenceClient) Preconditions.checkNotNull(this.applicationComponent.getGeofenceClient()));
        GeofenceTransitionsIntentService_MembersInjector.injectMWeatherUtil(geofenceTransitionsIntentService, (WeatherUtil) Preconditions.checkNotNull(this.applicationComponent.getWeatherUtil()));
        return geofenceTransitionsIntentService;
    }

    private GeofencesUpdateService injectGeofencesUpdateService(GeofencesUpdateService geofencesUpdateService) {
        GeofencesUpdateService_MembersInjector.injectDataManager(geofencesUpdateService, (DataManager) Preconditions.checkNotNull(this.applicationComponent.getDataManager()));
        GeofencesUpdateService_MembersInjector.injectLocationUtil(geofencesUpdateService, (LocationUtil) Preconditions.checkNotNull(this.applicationComponent.getLocationUtil()));
        GeofencesUpdateService_MembersInjector.injectSchedulerProvider(geofencesUpdateService, ServiceModule_ProvideSchedulerProviderFactory.provideSchedulerProvider(this.serviceModule));
        GeofencesUpdateService_MembersInjector.injectCompositeDisposable(geofencesUpdateService, ServiceModule_ProvideCompositeDisposableFactory.provideCompositeDisposable(this.serviceModule));
        return geofencesUpdateService;
    }

    public static final class Builder {
        private ApplicationComponent applicationComponent;
        private ServiceModule serviceModule;

        private Builder() {
        }

        public Builder serviceModule(ServiceModule serviceModule) {
            this.serviceModule = (ServiceModule) Preconditions.checkNotNull(serviceModule);
            return this;
        }

        public Builder applicationComponent(ApplicationComponent applicationComponent) {
            this.applicationComponent = (ApplicationComponent) Preconditions.checkNotNull(applicationComponent);
            return this;
        }

        public ServiceComponent build() {
            Preconditions.checkBuilderRequirement(this.serviceModule, ServiceModule.class);
            Preconditions.checkBuilderRequirement(this.applicationComponent, ApplicationComponent.class);
            return new DaggerServiceComponent(this.serviceModule, this.applicationComponent);
        }
    }
}
