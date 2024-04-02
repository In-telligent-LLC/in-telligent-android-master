package com.sca.in_telligent.util.geofence;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.openapi.data.network.model.IntelligentGeofence;
import com.sca.in_telligent.receiver.GeofenceBroadcastReceiver;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.TimeoutLocationListener;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Comparator;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;



@Singleton
public class AppGeofenceClient implements GeofenceClient {

  private static final String TAG = "AppGeofenceClient";

  private final GeofencingClient mGeofencingClient;
  private final LocationUtil mLocationUtil;
  private final DataManager mDataManager;
  private final SchedulerProvider mSchedulerProvider;
  private final CompositeDisposable mCompositeDisposable;
  private final Context mContext;

  private PendingIntent mGeofencePendingIntent;

  ArrayList<Geofence> pendingGeofences = new ArrayList<>();
  ArrayList<IntelligentGeofence> fetchedGeofences = new ArrayList<>();

  @Inject
  public AppGeofenceClient(GeofencingClient geofencingClient, LocationUtil locationUtil,
      DataManager dataManager, SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable, @ApplicationContext Context context) {
        mGeofencingClient = geofencingClient;
        mLocationUtil = locationUtil;
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
        mContext = context;
  }

  public void populateIntelligentFences(boolean useCache) {
    mLocationUtil.getCurrentLocation(new TimeoutLocationListener(5_000) {
      @Override
      public void onTimeout() {
        System.out.println();
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

                  intelligentGeofenceResponse.getIntelligentGeofences().sort(Comparator
                          .comparing(geofence -> geofence.getLocation().distanceTo(location)));

                  fetchedGeofences = intelligentGeofenceResponse.getIntelligentGeofences();
                  addGeofence(fetchedGeofences, location);
                  Log.i(TAG, "onLocationAvailable: add geofences");
                }, throwable -> {
                      Log.e(TAG, "onLocationAvailable: error", throwable);
                }));
      }
    });
  }

  @SuppressLint("MissingPermission")
  private void addGeofence(ArrayList<IntelligentGeofence> geofenceArrayList, Location location) {

    Disposable disposable = Completable.fromAction(() -> {
      int count = 0;
      IntelligentGeofence lastGeofence = null;

      for (IntelligentGeofence geofence : geofenceArrayList) {
        Geofence fence = createGeofence(geofence);
        pendingGeofences.add(fence);
        lastGeofence = geofence;
        if (++count == 19) {
          break;
        }
      }

      if (lastGeofence != null) {
        pendingGeofences.add(createGeofenceFromLocation(location, lastGeofence));
      }

      mGeofencingClient
              .addGeofences(getGeofencingRequest(pendingGeofences), getGeofencePendingIntent())
              .addOnSuccessListener(aVoid -> {
                System.out.println();
              }).addOnFailureListener(e -> {
                Log.e(TAG, "");

      });
    }).subscribeOn(mSchedulerProvider.computation())
            .observeOn(mSchedulerProvider.ui())
            .subscribe(
                    () -> {
                      System.out.println();
                    },
                    error -> {
                      System.out.println();
                    }
            );

    mCompositeDisposable.add(disposable);
  }

  private GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofences) {
    GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
    builder.setInitialTrigger(
        GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_EXIT);
    builder.addGeofences(geofences);
    return builder.build();
  }

  private PendingIntent getGeofencePendingIntent() {
    if (mGeofencePendingIntent != null) {
      return mGeofencePendingIntent;
    }

    Intent intent = new Intent(mContext, GeofenceBroadcastReceiver.class);
    int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
    }
    mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, flags);
    return mGeofencePendingIntent;
  }


  private Geofence createGeofence(IntelligentGeofence intelligentGeofence) {
    Geofence.Builder builder = new Geofence.Builder();

    builder.setCircularRegion(intelligentGeofence.getLat(), intelligentGeofence.getLng(),
        Math.max(100, intelligentGeofence.getRadius()))
        .setExpirationDuration(Geofence.NEVER_EXPIRE)
        .setRequestId(intelligentGeofence.getIdString())
        .setTransitionTypes(
            Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

    return builder.build();
  }

  @SuppressLint("Range")
  private Geofence createGeofenceFromLocation(Location location,
                                              IntelligentGeofence intelligentGeofence) {
    Geofence.Builder builder = new Geofence.Builder();
    builder.setCircularRegion(location.getLatitude(), location.getLongitude(),
        location.distanceTo(intelligentGeofence.getLocation()))
        .setExpirationDuration(Geofence.NEVER_EXPIRE)
        .setRequestId("IntelligentFence")
        .setTransitionTypes(
            Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

    return builder.build();
  }
}
