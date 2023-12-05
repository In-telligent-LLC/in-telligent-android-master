package com.sca.in_telligent.openapi.data.prefs;


import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscriberOptOut;

import java.util.ArrayList;

public interface OpenApiPreferencesHelper {

  String getAccessToken();

  void setAccessToken(String accessToken);

  void setLifeSafetyOverrideExpire(String dateExpire);

  String getLifeSafetyOverrideExpire();

  void setZipcode(String zipcode);

  String getZipcode();

  void setSubscribedBuildings(ArrayList<Building> subscribedBuildings);

  ArrayList<Building> getSubscribedBuildings();

  void setAutoOptOuts(ArrayList<SubscriberOptOut> optOuts);

  ArrayList<SubscriberOptOut> getAutoOptOuts();

  void setSubscriberId(String subscriberId);

  String getSubscriberId();

  long getLastFetchedGeofences();

  void setLastFetchedGeofences(long timeInMillis);
}
