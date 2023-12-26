package com.sca.in_telligent.openapi.util;

import android.os.Build;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class CommonUtils {
    public static String getDeviceInfo() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public static String getDeviceOs() {
        return Build.VERSION.RELEASE;
    }
}
