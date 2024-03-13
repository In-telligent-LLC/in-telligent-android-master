package com.sca.in_telligent.util;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.sca.in_telligent.BuildConfig;
import com.sca.in_telligent.R;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * Created by Marcos Ambrosi on 2/7/19.
 */
public class AppUpdateHandler {

    private static final int REMOTE_PARAMS_RELEASE_CACHE = 36000;
    private static final String MIN_SUPPORTED_VERSION_KEY = "min_supported_app_version";

    private static final String TAG = AppUpdateHandler.class.getSimpleName();

    public void start(@NonNull Context context) {
        requireNonNull(context, "Context cannot be null");

//        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(BuildConfig1.DEBUG)
//                .build();
//        firebaseRemoteConfig.setConfigSettings(configSettings);
//        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
//
//
//        firebaseRemoteConfig.fetch(BuildConfig1.DEBUG ? 0 : REMOTE_PARAMS_RELEASE_CACHE)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // After config data is successfully fetched, it must be activated before newly fetched
//                        // values are returned.
//                        firebaseRemoteConfig.activateFetched();
//
//                        if (BuildConfig1.VERSION_CODE < firebaseRemoteConfig.getLong(MIN_SUPPORTED_VERSION_KEY)) {
//                            showUpdateDialog(context);
//                        }
//
//                    } else {
//                        Log.e(TAG, "Fetch remote config failed", task.getException());
//                    }
//                });
    }

    private void showUpdateDialog(@NonNull Context context) {

        if (context == null) {
            Log.e(TAG, "showUpdateDialog: context == null");
            return;
        }

        AlertDialog alertDialog = (new AlertDialog.Builder(context)).
                setTitle(R.string.version_no_longer_available).
                setMessage(R.string.this_version_is_no_longer_supported).
                setCancelable(false).
                setPositiveButton(R.string.update, (dialog, which) -> {
                    goToPlayStore(context);
                }).create();
        alertDialog.show();
    }

    private void goToPlayStore(@NonNull Context context) {

        if (context == null) {
            Log.e(TAG, "goToPlayStore: context == null");
            return;
        }

        final String appPackageName = BuildConfig.APPLICATION_ID;
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException exception) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
