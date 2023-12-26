package com.sca.in_telligent.openapi.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class StoredData {
    private static final String TOTAL_SILENCE_EXPIRATION_KEY = "life_safety_override_expires";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Gson gsonBuilder = new GsonBuilder().create();

    private StoredData() {
    }

    private static void storeDate(Context context, String str, Date date) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0);
        if (date == null) {
            sharedPreferences.edit().remove(str).apply();
        } else {
            sharedPreferences.edit().putString(str, dateFormat.format(date)).apply();
        }
    }

    private static Date loadDate(Context context, String str) {
        String string = context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0).getString(str, null);
        if (string == null) {
            return null;
        }
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            Log.e("StoredData", "Failed to parse date: " + string, e);
            return null;
        }
    }

    private static void storeString(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0);
        if (str2 == null) {
            sharedPreferences.edit().remove(str).apply();
        } else {
            sharedPreferences.edit().putString(str, str2).apply();
        }
    }

    private static String loadString(Context context, String str) {
        return context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0).getString(str, null);
    }

    private static <T> void storeObject(Context context, String str, T t) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0);
        if (t == null) {
            sharedPreferences.edit().remove(str).apply();
        } else {
            sharedPreferences.edit().putString(str, gsonBuilder.toJson(t, t.getClass())).apply();
        }
    }

    private static <T> T loadObject(Context context, String str, Class<T> cls) {
        return (T) gsonBuilder.fromJson(context.getSharedPreferences(OpenApiPreferencesHelperImpl.PREF_NAME, 0).getString(str, null), (Class<Object>) cls);
    }

    public static void setTotalSilenceExpiration(Context context, Date date) {
        storeDate(context, TOTAL_SILENCE_EXPIRATION_KEY, date);
    }

    public static Date getTotalSilenceExpiration(Context context) {
        return loadDate(context, TOTAL_SILENCE_EXPIRATION_KEY);
    }

    public static void setAuthToken(Context context, String str) {
        storeString(context, Constants.Preferences.PREF_KEY_ACCESS_TOKEN, str);
    }

    public static String getAuthToken(Context context) {
        return loadString(context, Constants.Preferences.PREF_KEY_ACCESS_TOKEN);
    }
}
