package com.sca.in_telligent.ui.main;

import android.location.Location;
import android.os.Build;
import android.util.Log;
import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.CommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.SearchCommunityResponse;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.SubscriberResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.ui.main.MainMvpView;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.tbruyelle.rxpermissions2.Permission;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private static final int NO_INVITE_ID = -1112;
    private static final String TAG = "MainPresenter";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onClickAd$13(SuccessResponse successResponse) throws Exception {
    }

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public boolean isLoggedIn() {
        return getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType();
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void getSubscriber() {
        getSubscriber(true);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void getSubscriber(boolean z) {
        if (z) {
            ((MainMvpView) getMvpView()).showLoading();
        }
        getCompositeDisposable().add(getDataManager().getCurrentSubscriber().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m270xddfabae8((SubscriberResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m271xa0e72447((Throwable) obj);
            }
        }));
    }

    /* renamed from: lambda$getSubscriber$0$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m270xddfabae8(SubscriberResponse subscriberResponse) throws Exception {
        if (subscriberResponse.getSubscriber().getBuildings().size() == 0 && subscriberResponse.getSubscriber().getPersonalCommunities().size() == 0) {
            ((MainMvpView) getMvpView()).hideLoading();
            return;
        }
        ((MainMvpView) getMvpView()).loadSubscriber(subscriberResponse.getSubscriber());
        ((MainMvpView) getMvpView()).loadGroups(subscriberResponse.getSubscriber().getBuildings(), subscriberResponse.getSubscriber().getPersonalCommunities(), subscriberResponse.getSubscriber().getUser().getBuildingIds());
        getDataManager().setSubscriberId(Integer.toString(subscriberResponse.getSubscriber().getId()));
        getDataManager().setSubscribedBuildings(subscriberResponse.getSubscriber().getBuildings());
        getDataManager().setAutoOptOuts(subscriberResponse.getSubscriber().getSubscriberOptOuts());
    }

    /* renamed from: lambda$getSubscriber$1$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m271xa0e72447(Throwable th) throws Exception {
        ((MainMvpView) getMvpView()).hideLoading();
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void getSuggestedGroups() {
        getCompositeDisposable().add(getDataManager().getSuggestedGroups().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SearchCommunityResponse>() { // from class: com.sca.in_telligent.ui.main.MainPresenter.1
            @Override // io.reactivex.functions.Consumer
            public void accept(SearchCommunityResponse searchCommunityResponse) throws Exception {
                ((MainMvpView) MainPresenter.this.getMvpView()).loadSuggestedGroups(searchCommunityResponse.getBuildings());
            }
        }, new Consumer<Throwable>() { // from class: com.sca.in_telligent.ui.main.MainPresenter.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                ((MainMvpView) MainPresenter.this.getMvpView()).hideLoading();
            }
        }));
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void requestLocationPermissions(final boolean z) {
        if (Build.VERSION.SDK_INT >= 29) {
            getRxPermissions().request("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION").subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda16
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainPresenter.this.m273xf398e7ea(z, (Boolean) obj);
                }
            });
        } else {
            getRxPermissions().request("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION").subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainPresenter.this.m274xb6855149(z, (Boolean) obj);
                }
            });
        }
    }

    /* renamed from: lambda$requestLocationPermissions$2$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m273xf398e7ea(boolean z, Boolean bool) throws Exception {
        ((MainMvpView) getMvpView()).locationPermissionResult(bool.booleanValue(), z);
    }

    /* renamed from: lambda$requestLocationPermissions$3$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m274xb6855149(boolean z, Boolean bool) throws Exception {
        ((MainMvpView) getMvpView()).locationPermissionResult(bool.booleanValue(), z);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void requestPhonePermission() {
        getRxPermissions().requestEach("android.permission.CALL_PHONE").subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m275xb8bdb61a((Permission) obj);
            }
        });
    }

    /* renamed from: lambda$requestPhonePermission$4$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m275xb8bdb61a(Permission permission) throws Exception {
        ((MainMvpView) getMvpView()).phonePermissionResult(permission);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void subscribeToCommunity(int i) {
        subscribeToCommunity(i, NO_INVITE_ID);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void subscribeToCommunity(int i, int i2) {
        ((MainMvpView) getMvpView()).showLoading();
        getCompositeDisposable().add(getDataManager().updateSubscriptionToCommunity(new SubscribeToCommunityRequest(i, false, i2, SubscribeToCommunityRequest.ACTION_SUBSCRIBE)).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m276xc863eb43((SuccessResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m277x8b5054a2((Throwable) obj);
            }
        }));
    }

    /* renamed from: lambda$subscribeToCommunity$5$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m276xc863eb43(SuccessResponse successResponse) throws Exception {
        getSubscriber();
        ((MainMvpView) getMvpView()).showSubscriptionSuccessfulMessage();
    }

    /* renamed from: lambda$subscribeToCommunity$6$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m277x8b5054a2(Throwable th) throws Exception {
        ((MainMvpView) getMvpView()).hideLoading();
        ((MainMvpView) getMvpView()).onError(R.string.there_was_error_sending_request);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void onAppOpened() {
        getCompositeDisposable().add(getDataManager().appOpened().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SuccessResponse successResponse = (SuccessResponse) obj;
                Log.i(MainPresenter.TAG, "onAppOpened success");
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e(MainPresenter.TAG, "onAppOpened error", (Throwable) obj);
            }
        }));
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void refreshGeofences(Location location) {
        getCompositeDisposable().add(getDataManager().refreshGeofences(new LocationModel(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), location.getAccuracy())).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m272x10a14e6b((SuccessResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Throwable th = (Throwable) obj;
                Log.d(MainPresenter.TAG, "Failed to refresh geofences");
            }
        }));
    }

    /* renamed from: lambda$refreshGeofences$9$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m272x10a14e6b(SuccessResponse successResponse) throws Exception {
        getDataManager().setLastFetchedGeofences(System.currentTimeMillis());
        getSubscriber(false);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void getCommunityInfo(final int i, final int i2) {
        getCompositeDisposable().add(getDataManager().getCommunity(i).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m268x6c3a1b0f(i, i2, (CommunityResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m269x2f26846e((Throwable) obj);
            }
        }));
    }

    /* renamed from: lambda$getCommunityInfo$11$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m268x6c3a1b0f(int i, int i2, CommunityResponse communityResponse) throws Exception {
        ((MainMvpView) getMvpView()).showSubscribeToCommunityDialog(communityResponse.getBuilding().getName(), i, i2);
    }

    /* renamed from: lambda$getCommunityInfo$12$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m269x2f26846e(Throwable th) throws Exception {
        ((MainMvpView) getMvpView()).onError(R.string.there_was_error_connecting);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void startFetchingAds() {
        new Timer().schedule(new TimerTask() { // from class: com.sca.in_telligent.ui.main.MainPresenter.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                MainPresenter.this.fetchAd();
            }
        }, 0L, 30000L);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpPresenter
    public void onClickAd(int i) {
        getCompositeDisposable().add(getDataManager().clickAd(i).subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.lambda$onClickAd$13((SuccessResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e(MainPresenter.TAG, ((Throwable) obj).getMessage());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchAd() {
        getCompositeDisposable().add(getDataManager().getAd().subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainPresenter.this.m267lambda$fetchAd$15$comscain_telligentuimainMainPresenter((AdResponse) obj);
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ui.main.MainPresenter$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e(MainPresenter.TAG, ((Throwable) obj).getMessage());
            }
        }));
    }

    /* renamed from: lambda$fetchAd$15$com-sca-in_telligent-ui-main-MainPresenter  reason: not valid java name */
    public /* synthetic */ void m267lambda$fetchAd$15$comscain_telligentuimainMainPresenter(AdResponse adResponse) throws Exception {
        ((MainMvpView) getMvpView()).loadImage(adResponse.getBannerAd());
    }
}
