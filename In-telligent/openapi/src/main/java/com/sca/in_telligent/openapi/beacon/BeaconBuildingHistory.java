package com.sca.in_telligent.openapi.beacon;

import android.util.Log;

import com.sca.in_telligent.openapi.data.network.model.IntelligentBeacon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Marcos Ambrosi on 2/22/19.
 */
public class BeaconBuildingHistory {

    private static final String TAG = BeaconBuildingHistory.class.getSimpleName();

    public static final int MINIMUM_BEACONS_NEEDED_TO_DETERMINE_EVENT = 2;

    private List<BeaconHistoryItem> beaconHistoryItems = new ArrayList<>();

    private UserEvent lastEvent = UserEvent.UNDETERMINED;

    private boolean subscribedByBeacon = false;

    public enum UserEvent {
        UNDETERMINED,
        ENTERED_BUILDING,
        LEFT_BUILDING,
    }

    public boolean isSubscribedByBeacon() {
        return subscribedByBeacon;
    }

    public void setSubscribedByBeacon(boolean subscribedByBeacon) {
        this.subscribedByBeacon = subscribedByBeacon;
    }

    private void determineEvent() {
        Log.d(TAG, "determineEvent() called lastEvent: " + lastEvent + " beaconHistoryItems: " + beaconHistoryItems);

        if (beaconHistoryItems.size() <= MINIMUM_BEACONS_NEEDED_TO_DETERMINE_EVENT) {
            this.lastEvent = UserEvent.UNDETERMINED;
            return;
        }

        final List<BeaconHistoryItem> sortedList = beaconHistoryItems.stream()
                .sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());

        if (sortedList.get(0).intelligentBeacon.getType() == sortedList.get(0).intelligentBeacon.getType()) {
            Log.e(TAG, "determineEvent: the nearest two beacons are of the same type," +
                    " so it's not possible to determine the event");
            this.lastEvent = UserEvent.UNDETERMINED;
            return;
        }

        if (sortedList.get(0).intelligentBeacon.getType() == IntelligentBeacon.BeaconType.INSIDE) {
            this.lastEvent = UserEvent.ENTERED_BUILDING;
            return;
        } else {
            this.lastEvent = UserEvent.LEFT_BUILDING;
            return;
        }

    }

    public void onBeaconFound(IntelligentBeacon beacon, double distance) {
        BeaconHistoryItem foundItem = beaconHistoryItems.stream()
                .filter(beaconHistoryItem -> beaconHistoryItem
                        .intelligentBeacon.getType() == beacon.getType())
                .findFirst().orElse(null);

        if (foundItem != null) {
            foundItem.distance = distance;
        } else {
            beaconHistoryItems.add(new BeaconHistoryItem(beacon, distance));
        }

        this.determineEvent();
    }

    public UserEvent getLastEvent() {
        return lastEvent;
    }

    public List<BeaconHistoryItem> getBeaconHistoryItems() {
        return beaconHistoryItems;
    }

    public static class BeaconHistoryItem {
        private IntelligentBeacon intelligentBeacon;
        private double distance;

        public BeaconHistoryItem(IntelligentBeacon intelligentBeacon, double distance) {
            this.intelligentBeacon = intelligentBeacon;
            this.distance = distance;
        }

        public IntelligentBeacon getIntelligentBeacon() {
            return intelligentBeacon;
        }

        public double getDistance() {
            return distance;
        }

        public int compareTo(BeaconHistoryItem other) {
            return new Double(this.distance).compareTo(new Double(other.distance));
        }

        @Override
        public String toString() {
            return "BeaconHistoryItem{" +
                    "intelligentBeacon=" + intelligentBeacon +
                    ", distance=" + distance +
                    '}';
        }
    }
}
