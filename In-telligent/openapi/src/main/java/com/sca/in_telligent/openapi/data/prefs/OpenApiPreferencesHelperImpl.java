package com.sca.in_telligent.openapi.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscriberOptOut;
import java.util.ArrayList;

public class OpenApiPreferencesHelperImpl implements OpenApiPreferencesHelper {
    public static final String PREF_NAME = "auth";
    public final SharedPreferences mPrefs;

    public OpenApiPreferencesHelperImpl(Context context, String str) {
        this.mPrefs = context.getSharedPreferences(str, 0);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getAccessToken() {
        return this.mPrefs.getString(Constants.Preferences.PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setAccessToken(String str) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_KEY_ACCESS_TOKEN, str).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setLifeSafetyOverrideExpire(String str) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_KEY_LIFESAFETY_OVERRIDE_EXPIRE, str).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getLifeSafetyOverrideExpire() {
        return this.mPrefs.getString(Constants.Preferences.PREF_KEY_LIFESAFETY_OVERRIDE_EXPIRE, null);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setZipcode(String str) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_USER_ZIPCODE, str).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getZipcode() {
        return this.mPrefs.getString(Constants.Preferences.PREF_USER_ZIPCODE, null);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setSubscribedBuildings(ArrayList<Building> arrayList) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_SUBSCRIBED_BUILDINGS, new Gson().toJson(arrayList)).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public ArrayList<Building> getSubscribedBuildings() {
        return (ArrayList) new Gson().fromJson(this.mPrefs.getString(Constants.Preferences.PREF_SUBSCRIBED_BUILDINGS, null), new TypeToken<ArrayList<Building>>() { // from class: com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl.1
        }.getType());
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setAutoOptOuts(ArrayList<SubscriberOptOut> arrayList) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_OPT_OUTS, new Gson().toJson(arrayList)).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public ArrayList<SubscriberOptOut> getAutoOptOuts() {
        return (ArrayList) new Gson().fromJson(this.mPrefs.getString(Constants.Preferences.PREF_OPT_OUTS, null), new TypeToken<ArrayList<SubscriberOptOut>>() { // from class: com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl.2
        }.getType());
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setSubscriberId(String str) {
        this.mPrefs.edit().putString(Constants.Preferences.PREF_KEY_SUBSCRIBER_ID, str).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public String getSubscriberId() {
        return this.mPrefs.getString(Constants.Preferences.PREF_KEY_SUBSCRIBER_ID, null);
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public void setLastFetchedGeofences(long j) {
        this.mPrefs.edit().putLong(Constants.Preferences.PREF_LAST_FETCHED_GEOFENCES, j).apply();
    }

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    public long getLastFetchedGeofences() {
        return this.mPrefs.getLong(Constants.Preferences.PREF_LAST_FETCHED_GEOFENCES, 0L);
    }
}
