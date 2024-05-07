package com.sca.in_telligent.ui.main;

import static com.sca.in_telligent.util.CommonUtils.buildAlertMessage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private static final int NO_INVITE_ID = -1112;
    private static final String TAG = "MainPresenter";
    private static final long LAST_CHECKED_EXPIRATION_IN_MILLIS = TimeUnit.MINUTES.toMillis(10);

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public boolean isLoggedIn() {
        return getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType();
    }

    @Override
    public void getSubscriber() {
        getSubscriber(true);
    }

    @Override
    public void getSubscriber(boolean showLoading) {
        if (showLoading) {
            getMvpView().showLoading();
        }
        getCompositeDisposable().add(
                getDataManager().getCurrentSubscriber().subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(
                                subscriberResponse -> {
                                    Log.d(TAG, "getSubscriber: " + subscriberResponse.getSubscriber().getBuildings().size());

                                    if (subscriberResponse.getSubscriber().getBuildings().size() == 0) {
                                        getMvpView().hideLoading();
                                    } else {
                                        getMvpView().loadSubscriber(subscriberResponse.getSubscriber());
                                        getMvpView().loadGroups(subscriberResponse.getSubscriber().getBuildings(),
                                                subscriberResponse.getSubscriber().getPersonalCommunities(),
                                                subscriberResponse.getSubscriber().getUser().getBuildingIds());

                                        getDataManager()
                                                .setSubscriberId(
                                                        Integer.toString(subscriberResponse.getSubscriber().getId()));
                                        getDataManager()
                                                .setSubscribedBuildings(subscriberResponse.getSubscriber().getBuildings());
                                        getDataManager()
                                                .setAutoOptOuts(subscriberResponse.getSubscriber().getSubscriberOptOuts());
                                    }
                                }, throwable -> {
                                    Log.e(TAG, throwable.getMessage());
                                    getMvpView().hideLoading();
                                }));
    }


    @Override
    public void getSuggestedGroups() {
        getCompositeDisposable().add(
                getDataManager().getSuggestedGroups().subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(
                                searchCommunityResponse -> {
                                    Log.d(TAG, "getSuggestedGroups: " + searchCommunityResponse.getBuildings());
                                    getMvpView().loadSuggestedGroups(searchCommunityResponse.getBuildings());
                                }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void requestDNDPermission(Context context) {
        if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).isNotificationPolicyAccessGranted()) {
            return;
        }
        buildAlertMessage(context.getString(R.string.permission_to_manage_dnd), context.getString(R.string.permission_to_manage_dnd_description), context);

    }

    @Override
    public void requestNotification(Context context) {
        if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).areNotificationsEnabled()) {
            return;
        }
        buildAlertMessage(context.getString(R.string.permission_to_manage_dnd), context.getString(R.string.permission_to_manage_dnd_description), context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void requestLocationPermissions(boolean phone) {
        getRxPermissions()
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> getMvpView().locationPermissionResult(granted, phone));
    }

    private static final int REQUEST_PHONE_PERMISSION = 1;

    @SuppressLint("CheckResult")
    @Override
    public void requestPhonePermission() {
        getRxPermissions().request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> getMvpView().phonePermissionResult(granted));

    }
    @Override
    public void subscribeToCommunity(int buildingId) {
        subscribeToCommunity(buildingId, NO_INVITE_ID);
    }

    @Override
    public void subscribeToCommunity(int buildingId, int inviteId) {
        getMvpView().showLoading();

        SubscribeToCommunityRequest subscribeToCommunityRequest = new SubscribeToCommunityRequest(buildingId,
                false, inviteId, SubscribeToCommunityRequest.ACTION_SUBSCRIBE);

        getCompositeDisposable().add(getDataManager().updateSubscriptionToCommunity(subscribeToCommunityRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
                    getSubscriber();
                    getMvpView().showSubscriptionSuccessfulMessage();
                }, throwable -> {
                    getMvpView().hideLoading();
                    getMvpView().onError(R.string.there_was_error_sending_request);
                }));
    }

    @Override
    public void onAppOpened() {
        getCompositeDisposable().add(getDataManager().appOpened()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(successResponse -> Log.i(TAG, "onAppOpened success"),
                        throwable -> Log.e(TAG, "onAppOpened error", throwable)));
    }

    public static void buildAlertMessage(String title, String message, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialogInterface, i) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    @Override
    public void refreshGeofences(Location location) {

        if (!shouldRefreshGeofences()) {
            Log.d(TAG, "refreshGeofences: No need to refresh geofences now");
            return;
        }

        LocationModel body = new LocationModel(location.getLatitude(),
                location.getLongitude(),
                location.getAccuracy());

        getCompositeDisposable().add(
                getDataManager().refreshGeofences(body)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(successResponse -> {
                            getDataManager().setLastFetchedGeofences(System.currentTimeMillis());
                            getSubscriber(false);
                        }, throwable -> {
                            Log.d(MainPresenter.TAG, "Failed to refresh geofences");
                        })
        );
    }


    private boolean shouldRefreshGeofences() {
        return (System.currentTimeMillis() - getDataManager().getLastFetchedGeofences()) >
                LAST_CHECKED_EXPIRATION_IN_MILLIS;
    }

    @Override
    public void getCommunityInfo(int communityId, int inviteId) {
        getCompositeDisposable().add(getDataManager().getCommunity(communityId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(communityResponse -> {
                    getMvpView().showSubscribeToCommunityDialog(communityResponse
                            .getBuilding().getName(), communityId, inviteId);
                }, throwable -> {
                    getMvpView().onError(R.string.there_was_error_connecting);
                }));
    }

    @Override
    public void startFetchingAds() {
        new Timer().schedule(new TimerTask() { // from class: com.sca.in_telligent.ui.main.MainPresenter.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                MainPresenter.this.fetchAd();
            }
        }, 0L, 30000L);
    }

    @Override
    public void onClickAd(int adId) {
        getCompositeDisposable().add(getDataManager().clickAd(adId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(
                        response -> {

                        }, throwable -> Log.e(TAG, throwable.getMessage())));
    }

    private void fetchAd() {
        getCompositeDisposable().add(getDataManager().getAd()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(
                        response -> {
                            getMvpView().loadImage(response.getBannerAd());
                        }, throwable -> {
                            Log.e(TAG, throwable.getMessage());
                        }));
    }

}
