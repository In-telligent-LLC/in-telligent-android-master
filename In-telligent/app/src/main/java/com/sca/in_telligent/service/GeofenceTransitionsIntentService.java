package com.sca.in_telligent.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import android.util.Log;

import androidx.core.app.JobIntentService;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.component.DaggerServiceComponent;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.IntelligentGeofence;
import com.sca.in_telligent.openapi.data.network.model.IntelligentGeofenceResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.di.component.ServiceComponent;
import com.sca.in_telligent.di.module.ServiceModule;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.TimeoutLocationListener;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class GeofenceTransitionsIntentService extends JobIntentService {

    private static final int JOB_ID = 573;
    private static final String TAG = "GeofenceTransitionsIntentService";
    private final String USE_CACHE = "use_cache";

    @Inject
    LocationUtil mLocationUtil;

    @Inject
    DataManager mDataManager;

    @Inject
    SchedulerProvider mSchedulerProvider;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    GeofenceClient mGeofenceClient;

    @Inject
    WeatherUtil mWeatherUtil;


    public static void start(Context context) {
        Intent starter = new Intent(context, GeofenceTransitionsIntentService.class);
        GeofenceTransitionsIntentService.enqueueWork(context, starter);
    }


    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, GeofenceTransitionsIntentService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceComponent component = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(((ScaApplication) getApplication()).getComponent())
                .build();
        component.inject(this);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        final boolean useCache = intent.getBooleanExtra(USE_CACHE, false);


        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent has errors");
            return;
        }


        mLocationUtil.getCurrentLocation(new TimeoutLocationListener(5000) {
            @Override
            public void onTimeout() {
                Log.d(TAG, "Get current location timeout");
            }

            @Override
            public void onLocationAvailable(Location location) {

                if (location == null) {
                    return;
                }
                String lat = Double.toString(location.getLatitude());
                String lng = Double.toString(location.getLongitude());

                mCompositeDisposable.add(
                        mDataManager.getGeofences(lat, lng).subscribeOn(mSchedulerProvider.io())
                                .observeOn(mSchedulerProvider.ui()).subscribe(
                                intelligentGeofenceResponse -> {

                                    processGetGeofencesResponse(intelligentGeofenceResponse, geofencingEvent);

                                }, throwable -> {
                                    Log.e(TAG, "Error getting geofences", throwable);
                                }));
            }
        });


    }


    private void processGetGeofencesResponse(IntelligentGeofenceResponse intelligentGeofenceResponse, GeofencingEvent geofencingEvent) {

        ArrayList<IntelligentGeofence> intelligentGeofences = intelligentGeofenceResponse.getIntelligentGeofences();
        if (intelligentGeofences == null || intelligentGeofences.isEmpty()) {
            Log.d(TAG, "No geofences for current location");
            return;
        }

        createMap(geofencingEvent,
                intelligentGeofences);
    }

    private void createMap(GeofencingEvent geofencingEvent,
                           ArrayList<IntelligentGeofence> intelligentGeofences) {
        int transition = geofencingEvent.getGeofenceTransition();

        Map<String, IntelligentGeofence> map = new HashMap<>();
        Observable.fromIterable(intelligentGeofences)
                .map(intelligentGeofence -> {
                    map.put(intelligentGeofence.getId(), intelligentGeofence);
                    return map;
                }).toList()
                .subscribe(intelligentGeofenceMap -> sendGeofence(map, geofencingEvent, transition));
    }

    private void sendGeofence(Map<String, IntelligentGeofence> map, GeofencingEvent geofencingEvent,
                              int transition) {

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        if (triggeringGeofences == null) {
            Log.e(TAG, "Triggering geofences is null");
            return;
        }

        for (Geofence target : triggeringGeofences) {
            processGeofence(map, target, transition);
        }
    }

    private void processGeofence(Map<String, IntelligentGeofence> geofences, Geofence target,
                                 int transition) {

        boolean entered = transition == Geofence.GEOFENCE_TRANSITION_ENTER;
        boolean left = transition == Geofence.GEOFENCE_TRANSITION_EXIT;

        Log.d("GeofenceService",
                "Geofencing " + target.getRequestId() + " " + (entered ? "Entered " : "Left "));

        if (target.getRequestId().equals("IntelligentFence") && left) {
            mGeofenceClient.populateIntelligentFences(true);
            return;
        }

//        if (target.getRequestId().contains("beacon")) {
//            OpenAPI.getInstance().startScanningBeacons(getApplicationContext());
//            return;
//        }

        final IntelligentGeofence geofence = geofences.get(target.getRequestId());

        if (geofence == null) {
            return;
        }

        if (geofence.getType().equalsIgnoreCase("weather-alert")) {
            mWeatherUtil
                    .handleWeatherAlert(geofence.getId());
        }

        Building building = null;
        for (Building b : mDataManager.getSubscribedBuildings()) {
            if (b.getId() == Integer.parseInt(geofence.getId())) {
                building = b;
                break;
            }
        }

        if (building != null) {
            Log.d("GeofenceService", "building = null");

            if (left && building.getBuildingsSubscriber().isAutomatic() && geofence.getType()
                    .equalsIgnoreCase("building")) {

                handleCommunitySubscription(building.getId(), SubscribeToCommunityRequest.ACTION_UNSUBSCRIBE);

            }

        } else {

            if (entered && !geofence.isSecure() && geofence.getType().equalsIgnoreCase("building")
                    && !mDataManager
                    .getAutoOptOuts().stream().anyMatch(
                            subscriberOptOut -> subscriberOptOut.getId() == Integer.parseInt(geofence.getId()))) {

                handleCommunitySubscription(building.getId(), SubscribeToCommunityRequest.ACTION_SUBSCRIBE);
            }
        }
    }

    private void handleCommunitySubscription(int buildingId, @SubscribeToCommunityRequest.SubscribeAction String action) {

        SubscribeToCommunityRequest subscribeToCommunityRequest =
                new SubscribeToCommunityRequest(buildingId,
                        true,
                        action);

        Disposable subscribe = mDataManager.updateSubscriptionToCommunity(subscribeToCommunityRequest)
                .observeOn(mSchedulerProvider.io())
                .subscribeOn(mSchedulerProvider.ui()).subscribe(successResponse -> {

                }, throwable -> Log.e(TAG, "There was an error trying to subscribe to the community with id " + buildingId, throwable));

        mCompositeDisposable.add(subscribe);

    }
}
