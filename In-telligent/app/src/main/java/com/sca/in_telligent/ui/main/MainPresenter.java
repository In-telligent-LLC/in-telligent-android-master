package com.sca.in_telligent.ui.main;

import android.location.Location;
import android.util.Log;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.SearchCommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

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
                                    if (subscriberResponse.getSubscriber().getBuildings().size() == 0
                                            && subscriberResponse.getSubscriber().getPersonalCommunities().size() == 0) {
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
                                }, throwable -> getMvpView().hideLoading()));
    }


    @Override
    public void getSuggestedGroups() {
        getCompositeDisposable().add(
                getDataManager().getSuggestedGroups().subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(
                                new Consumer<SearchCommunityResponse>() {
                                    @Override
                                    public void accept(SearchCommunityResponse searchCommunityResponse) throws Exception {
                                        getMvpView().loadSuggestedGroups(searchCommunityResponse.getBuildings());
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        getMvpView().hideLoading();
                                    }
                                }));
    }

    @Override
    public void requestLocationPermissions(boolean phone) {
//        getRxPermissions()
//                .request(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)
//                .subscribe(granted -> getMvpView().locationPermissionResult(granted, phone));
    }


    @Override
    public void requestPhonePermission() {
//        getRxPermissions()
//                .request(permission.CALL_PHONE)
//                .subscribe(granted -> getMvpView().phonePermissionResult(granted));
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

    /* renamed from: lambda$fetchAd$15$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m267lambda$fetchAd$15$comscain_telligentuimainMainPresenter(AdResponse adResponse) throws Exception {
        ((MainMvpView) getMvpView()).loadImage(adResponse.getBannerAd());
    }
}
