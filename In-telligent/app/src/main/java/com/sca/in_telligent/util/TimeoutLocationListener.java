package com.sca.in_telligent.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TimeoutLocationListener implements LocationListener {

    private final Timer timeoutTimer;

    private boolean finished;

    @Override
    public void onLocationChanged(Location location) {
        synchronized (this) {
            if(!finished) {
                finished = true;
                timeoutTimer.cancel();

                onLocationAvailable(location);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public abstract void onTimeout();

    public abstract void onLocationAvailable(Location location);

    public TimeoutLocationListener(long timeout) {
        timeoutTimer = new Timer();
        timeoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (TimeoutLocationListener.this) {
                    if(!finished) {
                        finished = true;
                        onTimeout();
                    }
                }
            }
        }, timeout);
    }
}
