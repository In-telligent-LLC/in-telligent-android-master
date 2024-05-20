package com.sca.in_telligent.openapi.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sca.in_telligent.openapi.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class StoredData {

    private StoredData() {
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Gson gsonBuilder = (new GsonBuilder()).create();

    private static void storeDate(Context ctx, String key, @Nullable Date value) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);

        if (value == null) {
            sharedPrefs.edit().remove(key).apply();
        } else {
            sharedPrefs.edit().putString(key, dateFormat.format(value)).apply();
        }
    }

    @Nullable
    private static Date loadDate(Context ctx, String key) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);
        String value = sharedPrefs.getString(key, null);

        if (value == null) {
            return null;
        }

        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            Log.e("StoredData", "Failed to parse date: " + value, e);
            return null;
        }
    }

    private static void storeString(Context ctx, String key, @Nullable String value) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);

        if (value == null) {
            sharedPrefs.edit().remove(key).apply();
        } else {
            sharedPrefs.edit().putString(key, value).apply();
        }
    }

    @Nullable
    private static String loadString(Context ctx, String key) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);

        return sharedPrefs.getString(key, null);
    }

    private static <T> void storeObject(Context ctx, String key, T value) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);

        if (value == null) {
            sharedPrefs.edit().remove(key).apply();
        } else {
            sharedPrefs.edit().putString(key, gsonBuilder.toJson(value, value.getClass())).apply();
        }
    }

    private static <T> T loadObject(Context ctx, String key, Class<T> objClass) {
        SharedPreferences sharedPrefs = ctx.getSharedPreferences("auth", Context.MODE_PRIVATE);

        String gson = sharedPrefs.getString(key, null);
        return gsonBuilder.fromJson(gson, objClass);
    }


    private static final String TOTAL_SILENCE_EXPIRATION_KEY = "life_safety_override_expires";

    public static void setTotalSilenceExpiration(Context ctx, @Nullable Date expiration) {
        storeDate(ctx, TOTAL_SILENCE_EXPIRATION_KEY, expiration);
    }

    @Nullable
    public static Date getTotalSilenceExpiration(Context ctx) {
        return loadDate(ctx, TOTAL_SILENCE_EXPIRATION_KEY);
    }

    public static void setAuthToken(Context ctx, @Nullable String authToken) {
        storeString(ctx, Constants.Preferences.PREF_KEY_ACCESS_TOKEN, authToken);
    }

    @Nullable
    public static String getAuthToken(Context ctx) {
        return loadString(ctx, Constants.Preferences.PREF_KEY_ACCESS_TOKEN);
    }


}
