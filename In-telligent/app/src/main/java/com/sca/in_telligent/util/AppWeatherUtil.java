package com.sca.in_telligent.util;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.di.ApplicationContext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppWeatherUtil implements WeatherUtil {

  private Context context;

  LocationUtil locationUtil;

  @Inject
  DataManager dataManager;

  @Inject
  public AppWeatherUtil(@ApplicationContext Context context, LocationUtil locationUtil) {
    this.context = context;
    this.locationUtil = locationUtil;
  }

  public void handleWeatherAlert(final String weatherAlertId) {
    if (weatherAlertId == null || weatherAlertId.isEmpty()) {
      return;
    }

    locationUtil.getCurrentLocation(new TimeoutLocationListener(20_000) {
      @Override
      public void onTimeout() {
        final Location location = locationUtil.getLastKnownLocation();
        if (location != null) {
          LocationModel locationModel = new LocationModel();
          locationModel.setLat(location.getLatitude());
          locationModel.setLng(location.getLongitude());
          locationModel.setAccuracy(location.getAccuracy());
          sendWeatherAlert(weatherAlertId, locationModel);
        } else {

          if (dataManager.getZipcode() != null) {
            LocationModel locationModel = new LocationModel();
            LatLng latLng = locationUtil.getLatLngFromZip(context, dataManager.getZipcode());
            locationModel.setLat(latLng.latitude);
            locationModel.setLng(latLng.longitude);
            locationModel.setAccuracy(1000.0f);
            sendWeatherAlert(weatherAlertId, locationModel);
          }

        }
      }

      @Override
      public void onLocationAvailable(Location location) {
        LocationModel locationModel = new LocationModel();
        locationModel.setLat(location.getLatitude());
        locationModel.setLng(location.getLongitude());
        locationModel.setAccuracy(location.getAccuracy());
      }
    });
  }

  private void sendWeatherAlert(String weatherAlertId, LocationModel locationModel) {
    dataManager.sendWeatherAlert(weatherAlertId, locationModel).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String response) throws Exception {
            Log.d("In-telligent", "weather response: " + response);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        });
  }
}
