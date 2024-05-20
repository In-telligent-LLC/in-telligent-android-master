package com.sca.in_telligent.openapi.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Marcos Ambrosi on 2/24/19.
 */
public class BluetoothUtil {

    public static boolean isBluetoothEnabled(@NonNull Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }
        return bluetoothAdapter.isEnabled();
    }
}
