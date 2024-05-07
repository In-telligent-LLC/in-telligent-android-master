package com.sca.in_telligent.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.sca.in_telligent.di.ApplicationContext;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@Singleton
public class AppLocationUtil implements LocationUtil {

  private final Context context;

  @Inject
  public AppLocationUtil(@ApplicationContext Context context) {
    this.context = context;
  }

  @SuppressLint({"MissingPermission", "CheckResult"})
  public void getCurrentLocation(final TimeoutLocationListener timeoutLocationListener) {

    LocationManager locationManager = (LocationManager) context
            .getSystemService(Context.LOCATION_SERVICE);
    if (checkLocationPermission()) {
      Criteria locationCriteria = new Criteria();
      locationCriteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);

      Completable.fromAction(() -> {
        locationManager.requestSingleUpdate(locationCriteria, timeoutLocationListener, Looper.getMainLooper());
      }).subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      () -> {
                      },
                      error -> {
                      }
              );
    }
  }

  @SuppressLint("MissingPermission")
  public Location getLastKnownLocation() {

    LocationManager locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);
    if (checkLocationPermission()) {
      return locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    } else {
      return null;
    }
  }

  public LatLng getLatLngFromZip(final Context context, final String zipCode) {
    final Geocoder geocoder = new Geocoder(context);
    try {
      final List<Address> addresses = geocoder.getFromLocationName(zipCode, 1);
      if (addresses != null && !addresses.isEmpty()) {
        return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
      } else {
        return null;
      }
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public boolean isProviderEnabled() {
    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  }

  private boolean checkLocationPermission() {

    return ActivityCompat
        .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED;
  }
}
