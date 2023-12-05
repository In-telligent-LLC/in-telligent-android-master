package com.sca.in_telligent.openapi.receiver;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.sca.in_telligent.openapi.service.BeaconScannerService;

/**
 * Created by Marcos Ambrosi on 2/21/19.
 */
public class BeaconScannerBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = BeaconScannerBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "onReceive: Location Permissions not granted");
            return;
        }


        final String action = intent.getAction();

        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.d(TAG, "received bluetooth connection state STATE_OFF");
                    stopBeaconScanningService(context);
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.d(TAG, "received bluetooth connection state STATE_ON");
                    startBeaconScanningService(context);
                    break;
            }
        }
    }

    private void startBeaconScanningService(Context context) {
        Log.d(TAG, "starting beacon scanning service...");
        Intent intent = BeaconScannerService.getIntent(context, BeaconScannerService.ACTION_START_FOREGROUND);
        context.startService(intent);
    }

    private void stopBeaconScanningService(Context context) {
        Log.d(TAG, "stopping beacon scanning service...");
        Intent intent = BeaconScannerService.getIntent(context, BeaconScannerService.ACTION_STOP_FOREGROUND);
        context.stopService(intent);
    }
}
