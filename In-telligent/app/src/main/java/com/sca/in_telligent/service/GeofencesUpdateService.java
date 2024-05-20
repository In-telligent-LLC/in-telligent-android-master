package com.sca.in_telligent.service;

import android.location.Location;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.component.DaggerServiceComponent;
import com.sca.in_telligent.di.component.ServiceComponent;
import com.sca.in_telligent.di.module.ServiceModule;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.TimeoutLocationListener;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class GeofencesUpdateService extends JobService {

    public static final String TAG = GeofencesUpdateService.class.getSimpleName();

    public static final int DEFAULT_INTERVAL_IN_MILLIS_START_WINDOW = Math.toIntExact(TimeUnit.
            MINUTES.toSeconds(20));

    public static final int DEFAULT_INTERVAL_IN_MILLIS_END_WINDOW = Math.toIntExact(TimeUnit.
            MINUTES.toSeconds(40));

    private static final long LOCATION_LISTENER_TIMEOUT = 5000;

    @Inject
    DataManager dataManager;

    @Inject
    LocationUtil locationUtil;

    @Inject
    SchedulerProvider schedulerProvider;

    @Inject
    CompositeDisposable compositeDisposable;


    public static Job create(FirebaseJobDispatcher dispatcher) {
        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setService(GeofencesUpdateService.class)
                .setTag(GeofencesUpdateService.TAG)
                .setReplaceCurrent(false)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(GeofencesUpdateService.DEFAULT_INTERVAL_IN_MILLIS_START_WINDOW,
                        GeofencesUpdateService.DEFAULT_INTERVAL_IN_MILLIS_END_WINDOW))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        return job;
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
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        locationUtil.getCurrentLocation(new TimeoutLocationListener(LOCATION_LISTENER_TIMEOUT) {
            @Override
            public void onTimeout() {
                markJobAsFinished(job);
            }

            @Override
            public void onLocationAvailable(Location location) {

                LocationModel body = new LocationModel(location.getLatitude(),
                        location.getLongitude(), location.getAccuracy());

                Disposable subscribe = dataManager.refreshGeofences(body)
                        .subscribeOn(schedulerProvider.io()).doOnComplete(() -> {
                            markJobAsFinished(job);
                        })
                        .observeOn(schedulerProvider.ui()).subscribe(intelligentGeofenceResponse -> {
                            dataManager.setLastFetchedGeofences(System.currentTimeMillis());

                        }, throwable -> Log.e(TAG, "accept: Error fetching geofences", throwable));

                compositeDisposable.add(subscribe);
            }
        });
        return true;
    }

    private void markJobAsFinished(JobParameters job) {
        jobFinished(job, false);
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


}
