package com.sca.in_telligent.openapi.util;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OpenApiFlashHelper implements FlashHelper {
    private static final String TAG = "OpenApiFlashHelper";
    private static boolean cameraRunning;
    private static CameraManager manager;
    private Context mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startFlashTask$1() throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startFlashTask$2(Throwable th) throws Exception {
    }

    private OpenApiFlashHelper(Context context) {
        this.mContext = context;
    }

    public static FlashHelper newInstance(Context context) {
        return new OpenApiFlashHelper(context);
    }

    @Override // com.sca.in_telligent.openapi.util.FlashHelper
    public void startFlashTask() {
        if (manager == null) {
            manager = (CameraManager) this.mContext.getSystemService(Context.CAMERA_SERVICE);
        }
        cameraRunning = true;
        Completable.fromAction(new Action() { // from class: com.sca.in_telligent.openapi.util.OpenApiFlashHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                OpenApiFlashHelper.lambda$startFlashTask$0();
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.sca.in_telligent.openapi.util.OpenApiFlashHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                OpenApiFlashHelper.lambda$startFlashTask$1();
            }
        }, new Consumer() { // from class: com.sca.in_telligent.openapi.util.OpenApiFlashHelper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                OpenApiFlashHelper.lambda$startFlashTask$2((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startFlashTask$0() throws Exception {
        String[] strArr;
        try {
            strArr = manager.getCameraIdList();
        } catch (CameraAccessException e) {
            Log.d(TAG, e.getMessage());
            strArr = null;
        }
        if (strArr == null) {
            return;
        }
        while (cameraRunning) {
            for (String str : strArr) {
                try {
                    manager.setTorchMode(str, true);
                } catch (Exception unused) {
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e2) {
                Log.e("GcmIntentService", "camera thread interrupted", e2);
            }
            for (String str2 : strArr) {
                try {
                    manager.setTorchMode(str2, false);
                } catch (Exception unused2) {
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e3) {
                Log.e("GcmIntentService", "camera thread interrupted", e3);
            }
        }
    }

    @Override // com.sca.in_telligent.openapi.util.FlashHelper
    public void stopFlashTask() {
        cameraRunning = false;
    }
}
