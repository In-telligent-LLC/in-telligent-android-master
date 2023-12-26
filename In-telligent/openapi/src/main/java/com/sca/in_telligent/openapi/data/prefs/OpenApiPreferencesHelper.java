package com.sca.in_telligent.openapi.data.prefs;

import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscriberOptOut;
import java.util.ArrayList;

public interface OpenApiPreferencesHelper {

    ArrayList<SubscriberOptOut> getAutoOptOuts();

    long getLastFetchedGeofences();

    String getLifeSafetyOverrideExpire();

    ArrayList<Building> getSubscribedBuildings();

    String getSubscriberId();

    String getZipcode();

    String getAccessToken();

    void setAccessToken(String str);

    void setAutoOptOuts(ArrayList<SubscriberOptOut> arrayList);

    void setLastFetchedGeofences(long j);

    void setLifeSafetyOverrideExpire(String str);

    void setSubscribedBuildings(ArrayList<Building> arrayList);

    void setSubscriberId(String str);

    void setZipcode(String str);
}
