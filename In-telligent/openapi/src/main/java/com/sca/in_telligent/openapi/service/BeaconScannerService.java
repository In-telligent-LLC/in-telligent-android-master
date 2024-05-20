package com.sca.in_telligent.openapi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sca.in_telligent.openapi.BuildConfig;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.R;
import com.sca.in_telligent.openapi.beacon.BeaconBuildingHistory;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.IntelligentBeacon;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.lang.annotation.Retention;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Marcos Ambrosi on 2/19/19.
 */
public class BeaconScannerService extends Service implements BeaconConsumer,
        RangeNotifier {

    private static final String TAG = BeaconScannerService.class.getSimpleName();
    private static final UUID BEACON_UUID = UUID.fromString("937DDCBE-1E3A-45C4-B8DE-06E9F6BB40B3");
    private static final String IBEACON_LAYOUT = "m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24";
    private static final int NOTIFICATION_ID = 9299;

    public static final String ACTION_START_FOREGROUND = "start_foreground";
    public static final String ACTION_STOP_FOREGROUND = "stop_foreground";

    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private BackgroundPowerSaver backgroundPowerSaver;
    private Map<Integer, List<IntelligentBeacon>> intelligentBeaconsBuildingMap = new ConcurrentHashMap<>();
    private Map<Integer, BeaconBuildingHistory> historyMap = new ConcurrentHashMap<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private OpenApiPreferencesHelper preferencesHelper;


    @Retention(SOURCE)
    @StringDef({
            ACTION_START_FOREGROUND,
            ACTION_STOP_FOREGROUND
    })
    public @interface ScannerIntentAction {
    }

    public static Intent getIntent(Context context, @ScannerIntentAction String action) {
        Intent intent = new Intent(context, BeaconScannerService.class);
        intent.setAction(action);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesHelper = new OpenApiPreferencesHelperImpl(getApplicationContext(), OpenApiPreferencesHelperImpl.PREF_NAME);
        loadIntelligentBeacons();
    }

    private void loadIntelligentBeacons() {
        if (!intelligentBeaconsBuildingMap.isEmpty()) {
            Log.d(TAG, "loadIntelligentBeacons: " +
                    "Trying to load In-telligent beacons when they were already loaded");
            return;
        }

        Disposable disposable = OpenAPI.getApiHelper().getAllBeacons()
                .map(beaconsResponse -> beaconsResponse.getBuildingBeaconsPairs())
                .flatMap(Observable::fromIterable)
                .doOnNext(beaconBuildingPair -> intelligentBeaconsBuildingMap
                        .put(beaconBuildingPair.getBuildingId(), beaconBuildingPair.getBeacons()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(beacons -> {

                }, exception -> {
                    Log.e(TAG, "updateRegisteredBeacons: " + exception.getMessage(), exception);
                });

        compositeDisposable.add(disposable);

    }

    private void launchNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String notificationChannel = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = createNotificationChannel(BeaconScannerService.class.getSimpleName().toLowerCase(),
                    BeaconScannerService.class.getName());
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                notificationChannel);

        Intent stopIntent = new Intent(this, BeaconScannerService.class);
        stopIntent.setAction(BeaconScannerService.ACTION_STOP_FOREGROUND);
        PendingIntent stopPendingIntent =
                PendingIntent.getService(this, 0, stopIntent, 0);

        builder.addAction(R.drawable.baseline_close_white_18, "Stop", stopPendingIntent);

        builder.setPriority(NotificationManager.IMPORTANCE_MIN);
        builder.setCategory(Notification.CATEGORY_SERVICE);
        builder.setContentTitle(getString(R.string.scanning_beacons));
        String contentText = getString(R.string.scanning_beacons_message);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(contentText));
        builder.setContentText(contentText);
        builder.setSmallIcon(R.drawable.baseline_bluetooth_searching_black_24);
        startForeground(NOTIFICATION_ID, builder.build());
        initBeaconManager();
    }

    private void initBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());

        beaconManager.unbind(this);
        beaconManager.removeAllRangeNotifiers();
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.setDebug(BuildConfig.DEBUG);

        //Add parsers
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.URI_BEACON_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(IBEACON_LAYOUT));

        backgroundPowerSaver = new BackgroundPowerSaver(this);

        beaconManager.bind(this);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    public void onDestroy() {
        unbindBeaconManager();
        notificationManager.cancel(NOTIFICATION_ID);
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void unbindBeaconManager() {
        if (beaconManager != null && beaconManager.isBound(this)) {
            beaconManager.removeAllRangeNotifiers();
            beaconManager.unbind(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START_FOREGROUND.equals(intent.getAction())) {
            launchNotification();
        } else if (ACTION_STOP_FOREGROUND.equals(intent.getAction())) {
            unbindBeaconManager();
            stopForeground(true);
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onBeaconServiceConnect() {
        if (intelligentBeaconsBuildingMap.isEmpty()) {
            Log.d(TAG, "onBeaconServiceConnect: there are no intelligent beacons loaded.");
            return;
        }

        beaconManager.addRangeNotifier(this);

        try {

            for (int buildingId : intelligentBeaconsBuildingMap.keySet()) {
                Region region = new Region(buildingId + "",
                        Identifier.fromUuid(BEACON_UUID),
                        Identifier.fromInt(buildingId), null);
                beaconManager.startRangingBeaconsInRegion(region);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        Log.d(TAG, "didRangeBeaconsInRegion() called with: beacons = [" + beacons + "], region = [" + region + "]");

        if (beacons.isEmpty()) {
            Log.d(TAG, "didRangeBeaconsInRegion: there are no beacons ranged.");
            return;
        }

        if (intelligentBeaconsBuildingMap.isEmpty()) {
            Log.d(TAG, "didRangeBeaconsInRegion: there are no intelligent beacons loaded.");
            return;
        }

        int buildingId = region.getId2().toInt();

        List<IntelligentBeacon> intelligentBeacons = intelligentBeaconsBuildingMap.get(buildingId);

        if (intelligentBeacons == null || intelligentBeacons.isEmpty()) {
            Log.d(TAG, "didRangeBeaconsInRegion: there are no intelligent beacons loaded for the building with id " + buildingId);
            return;
        }

        BeaconBuildingHistory beaconBuildingHistory = historyMap.get(buildingId);

        if (beaconBuildingHistory == null) {
            beaconBuildingHistory = new BeaconBuildingHistory();
            historyMap.put(buildingId, beaconBuildingHistory);
        }


        for (Beacon beacon : beacons) {
            Identifier namespaceId = beacon.getId1();
            Identifier instanceId = beacon.getId2();


            Log.d(TAG, "I see a beacon transmitting namespace id: " + namespaceId +
                    " and instance id: " + instanceId +
                    " approximately " + beacon.getDistance() + " meters away.");


            IntelligentBeacon foundIntelligentBeacon = intelligentBeacons
                    .stream()
                    .filter(beacon1 -> beacon1.isBeacon(beacon))
                    .findFirst()
                    .orElse(null);

            if (foundIntelligentBeacon == null) {
                Log.d(TAG, "didRangeBeaconsInRegion: there's no IntelligentBeacon found for beacon " + beacon.toString());
                continue;
            }

            beaconBuildingHistory.onBeaconFound(foundIntelligentBeacon, beacon.getDistance());
        }

        Building building = null;
        for (Building b : preferencesHelper.getSubscribedBuildings()) {
            if (b.getId() == buildingId) {
                building = b;
                break;
            }
        }

        if (BeaconBuildingHistory.UserEvent.ENTERED_BUILDING.equals(beaconBuildingHistory.getLastEvent())) {
            if (beaconBuildingHistory.isSubscribedByBeacon()) {
                Log.d(TAG, "didRangeBeaconsInRegion: is already subscribed");
                return;
            }

            if (building != null && building.getBuildingsSubscriber() != null && !building.getBuildingsSubscriber().isAutomatic()) {
                Log.d(TAG, "didRangeBeaconsInRegion: user has no auto-subscription set for building");
                return;
            }

            Log.d(TAG, "didRangeBeaconsInRegion: SUBSCRIBINGG!");
            handleCommunitySubscription(buildingId, SubscribeToCommunityRequest.ACTION_SUBSCRIBE, beaconBuildingHistory);

        } else if (BeaconBuildingHistory.UserEvent.LEFT_BUILDING.equals(beaconBuildingHistory.getLastEvent()) && !preferencesHelper
                .getAutoOptOuts().stream().anyMatch(
                        subscriberOptOut -> subscriberOptOut.getId() == buildingId)) {

            handleCommunitySubscription(buildingId, SubscribeToCommunityRequest.ACTION_SUBSCRIBE, beaconBuildingHistory);
            Log.d(TAG, "didRangeBeaconsInRegion: UNSUBSCRIBING!");
        } else {
            Log.d(TAG, "didRangeBeaconsInRegion: UNDETERMINED!");
        }


    }


    private void handleCommunitySubscription(int buildingId, final @SubscribeToCommunityRequest.SubscribeAction String action,
                                             final @NonNull BeaconBuildingHistory beaconBuildingHistory) {

        if (beaconBuildingHistory == null) {
            Log.e(TAG, "handleCommunitySubscription() " +
                    "called with: buildingId = [" + buildingId + "], " +
                    "action = [" + action + "], " +
                    "beaconBuildingHistory = [" + beaconBuildingHistory + "]");
            return;
        }

        SubscribeToCommunityRequest subscribeToCommunityRequest =
                new SubscribeToCommunityRequest(buildingId,
                        true,
                        action);

        Disposable subscribe = OpenAPI.getApiHelper().updateSubscriptionToCommunity(subscribeToCommunityRequest)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(successResponse -> {

                    beaconBuildingHistory.setSubscribedByBeacon(action.equals(SubscribeToCommunityRequest.ACTION_SUBSCRIBE));

                }, throwable -> Log.e(TAG, "There was an error trying to subscribe to the community with id " + buildingId, throwable));

        compositeDisposable.add(subscribe);

    }
}

