package com.sca.in_telligent.openapi.util;

import android.os.Build;

public class CommonUtils {
    public static String getDeviceInfo() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static String getDeviceOs() {
        return Build.VERSION.RELEASE;
    }
}
