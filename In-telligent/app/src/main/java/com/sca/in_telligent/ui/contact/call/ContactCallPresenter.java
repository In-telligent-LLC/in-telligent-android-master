package com.sca.in_telligent.ui.contact.call;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.VoipCallRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactCallPresenter<V extends ContactCallMvpView> extends
    BasePresenter<V> implements ContactCallMvpPresenter<V> {

    private static final String TAG = "ContactCallPresenter";
    private Disposable disposable;


    @Inject
    public ContactCallPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @SuppressLint("CheckResult")
    @Override
    public void requestRecordAudioPermission() {
        disposable = getRxPermissions()
                .request(permission.RECORD_AUDIO)
                .subscribe(granted -> {
                            Log.d(TAG, "RESULT: " + granted);
                            if (granted != null) {
                                getMvpView().recordAudioPermissionResult(granted);

                            } else {
                                getMvpView().recordAudioPermissionResult(false);
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "requestRecordAudioPermission: ", throwable);
                        });


    }
    @Override
    public void getVoipToken(Integer buildingId) {
        if (buildingId == null) {
            return;
        }

        VoipCallRequest voipCallRequest = new VoipCallRequest();
        voipCallRequest.setBuildingId(String.valueOf(buildingId));

        Log.d(TAG, "getVoipToken: " + buildingId);
        disposable = getDataManager().getVoipToken(voipCallRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(voipTokenResponse -> {
                    Log.d(TAG, "VOIP TokenResponse: " + voipTokenResponse);
                    if (voipTokenResponse != null) {
                        getMvpView().onVoipTokenReceived(voipTokenResponse);
                    }
                }, throwable -> {
                    Log.e(TAG, "getVoipToken: ", throwable);
                });


    }


}
