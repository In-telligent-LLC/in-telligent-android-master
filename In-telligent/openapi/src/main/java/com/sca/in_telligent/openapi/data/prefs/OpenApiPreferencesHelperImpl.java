package com.sca.in_telligent.openapi.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.SubscriberOptOut;


import java.lang.reflect.Type;
import java.util.ArrayList;

;


public class OpenApiPreferencesHelperImpl implements OpenApiPreferencesHelper {

    public static final String PREF_NAME = "auth";
    public final SharedPreferences mPrefs;

    public OpenApiPreferencesHelperImpl(Context context,
                                        String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(Constants.Preferences.PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }


    @Override
    public void setLifeSafetyOverrideExpire(String dateExpire) {
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_LIFESAFETY_OVERRIDE_EXPIRE, dateExpire).apply();
    }

    @Override
    public String getLifeSafetyOverrideExpire() {
        return mPrefs.getString(Constants.Preferences.PREF_KEY_LIFESAFETY_OVERRIDE_EXPIRE, null);
    }

    @Override
    public void setZipcode(String zipcode) {
        mPrefs.edit().putString(Constants.Preferences.PREF_USER_ZIPCODE, zipcode).apply();
    }

    @Override
    public String getZipcode() {
        return mPrefs.getString(Constants.Preferences.PREF_USER_ZIPCODE, null);
    }

    @Override
    public void setSubscribedBuildings(ArrayList<Building> managedBuildings) {
        Gson gson = new Gson();
        String managedJson = gson.toJson(managedBuildings);
        mPrefs.edit().putString(Constants.Preferences.PREF_SUBSCRIBED_BUILDINGS, managedJson).apply();
    }

    @Override
    public ArrayList<Building> getSubscribedBuildings() {
        String managedString = mPrefs.getString(Constants.Preferences.PREF_SUBSCRIBED_BUILDINGS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Building>>() {
        }.getType();
        ArrayList<Building> arrayList = gson.fromJson(managedString, type);
        return arrayList;
    }

    @Override
    public void setAutoOptOuts(ArrayList<SubscriberOptOut> optOuts) {
        Gson gson = new Gson();
        String optOutJson = gson.toJson(optOuts);
        mPrefs.edit().putString(Constants.Preferences.PREF_OPT_OUTS, optOutJson).apply();
    }

    @Override
    public ArrayList<SubscriberOptOut> getAutoOptOuts() {
        String optOutString = mPrefs.getString(Constants.Preferences.PREF_OPT_OUTS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SubscriberOptOut>>() {
        }.getType();
        ArrayList<SubscriberOptOut> arrayList = gson.fromJson(optOutString, type);
        return arrayList;
    }

    @Override
    public void setSubscriberId(String subscriberId) {
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_SUBSCRIBER_ID, subscriberId).apply();
    }

    @Override
    public String getSubscriberId() {
        return mPrefs.getString(Constants.Preferences.PREF_KEY_SUBSCRIBER_ID, null);
    }

    @Override
    public void setLastFetchedGeofences(long timeInMillis) {
        mPrefs.edit().putLong(Constants.Preferences.PREF_LAST_FETCHED_GEOFENCES, timeInMillis).apply();
    }

    @Override
    public long getLastFetchedGeofences() {
        return mPrefs.getLong(Constants.Preferences.PREF_LAST_FETCHED_GEOFENCES, 0);
    }
}
