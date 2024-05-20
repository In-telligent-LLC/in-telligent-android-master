package com.sca.in_telligent.openapi.util;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class OpenApiFlashHelper implements FlashHelper {

    private static final String TAG = "OpenApiFlashHelper";
    private static CameraManager manager;
    private static boolean cameraRunning;
    private Context mContext;

    private OpenApiFlashHelper(Context context) {
        mContext = context;
    }

    public static FlashHelper newInstance(Context context) {
        return new OpenApiFlashHelper(context);
    }

    @Override
    public void startFlashTask() {
        if (manager == null) {
            manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        }
        cameraRunning = true;
        Completable.fromAction(() -> {
            String[] cameras = null;
            try {
                cameras = manager.getCameraIdList();
            } catch (CameraAccessException e) {
                Log.d(TAG, e.getMessage());
            }

            if (cameras == null) {
                return;
            }

            while (cameraRunning) {

                for (String camera : cameras) {
                    try {
                        manager.setTorchMode(camera, true);
                    } catch (Exception ignored) {
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("GcmIntentService", "camera thread interrupted", e);
                }

                for (String camera : cameras) {
                    try {
                        manager.setTorchMode(camera, false);
                    } catch (Exception ignored) {
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("GcmIntentService", "camera thread interrupted", e);
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        },
                        error -> {
                        }
                );
    }

    public void stopFlashTask() {
        cameraRunning = false;
    }
}
