package com.sca.in_telligent;

import android.app.Application;
import android.media.AudioManager;
import android.util.Log;

import com.factual.android.FactualException;
import com.factual.android.ObservationGraph;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.di.component.ApplicationComponent;
import com.sca.in_telligent.di.component.DaggerActivityComponent;
import com.sca.in_telligent.di.module.ApplicationModule;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.LifecycleInterface;


import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ScaApplication extends Application {

    private static final String LOG = ScaApplication.class.getName();

    @Inject
    DataManager mDataManager;

    @Inject
    LifecycleInterface appLifecycleObserver;

    @Inject
    AudioManager audioManager;

    private ApplicationComponent mApplicationComponent;

    public void onCreate() {
        super.onCreate();

        //This should be initialized BEFORE DI. Otherwise it will throw an exception
        initOpenApi();

        mApplicationComponent = DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        initOG();


//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
//
//            if (!task.isSuccessful()) {
//                return;
//            }
//            String token = task.getResult().getToken();
//            if (mDataManager.getCurrentUserLoggedInMode() == LoggedInMode.LOGGED_IN_MODE_LOGGED_IN
//                    .getType()) {
//
//                sendRegistrationToServer(token);
//            }
//            if (mDataManager.getPushToken() == null) {
//                mDataManager.setPushToken(token);
//            }
//        });

//        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver);
    }

    private void initOpenApi() {
        OpenAPI.Configuration configuration = new OpenAPI.Configuration.Builder()
                .setAppVersion(BuildConfig.VERSION_CODE)
                .setDebug(BuildConfig.DEBUG).build();
        OpenAPI.init(this, configuration);
    }

    public String getCurrentState() {
        return appLifecycleObserver.getState();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    private void sendRegistrationToServer(String token) {
        PushTokenRequest pushTokenRequest = new PushTokenRequest();
        pushTokenRequest.setEnvironment("fcm");
        pushTokenRequest.setPushToken(token);
        mDataManager.registerPushToken(pushTokenRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    Log.i(LOG, "sendRegistrationToServer: successResponse");
                }, throwable -> Log.e(LOG, "sendRegistrationToServer error ", throwable));
    }

    public void initOG() {
        if (CommonUtils.checkLocationPermission(this)) {
            try {
                ObservationGraph.getInstance(this, getString(R.string.factual_api_key));
            } catch (FactualException e) {
                Log.e(LOG, "Factual Exception: " + e);
            }
        } else {
        }
    }


//    public void initGroundTruth() {
//        if (CommonUtils.checkLocationPermission(this)) {
//            new LocationServiceClient(getString(R.string.groundtruth_access_key),
//                    getString(R.string.groundtruth_aes_key))
//                    .start(this);
//        }
//    }

}
