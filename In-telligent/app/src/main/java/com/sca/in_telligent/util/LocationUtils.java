package com.sca.in_telligent.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;

public class LocationUtils {
    public static final int LOCATION_REQUEST_PERMISSIONS_REQUEST_CODE = 14;
    public static final int REQUEST_CHECK_SETTINGS = 1;

    public static boolean neverAskAgainSelected(Activity activity) {
        return getRatinaleDisplayStatus(activity) != activity.shouldShowRequestPermissionRationale("android.permission.ACCESS_FINE_LOCATION");
    }

    public static boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public static boolean isPermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_BACKGROUND_LOCATION") == 0;
        } else return ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public static void sendLocationPermissionRequest(Activity activity) {
        ArrayList arrayList = new ArrayList(Arrays.asList("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"));
        if (Build.VERSION.SDK_INT == 29) {
            arrayList.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        ActivityCompat.requestPermissions(activity, (String[]) arrayList.toArray(new String[0]), 14);
    }

    public static void setShouldShowStatus(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("GENERIC_PREFERENCES", 0).edit();
        edit.putBoolean("android.permission.ACCESS_BACKGROUND_LOCATION", true);
        edit.commit();
    }

    public static boolean getRatinaleDisplayStatus(Context context) {
        return context.getSharedPreferences("GENERIC_PREFERENCES", 0).getBoolean("android.permission.ACCESS_BACKGROUND_LOCATION", false);
    }
}
