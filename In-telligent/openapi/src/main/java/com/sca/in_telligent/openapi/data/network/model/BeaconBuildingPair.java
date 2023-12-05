package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Marcos Ambrosi on 2/18/19.
 */
public class BeaconBuildingPair {
    @SerializedName("id")
    private int buildingId;

    @SerializedName("beacons")
    private List<IntelligentBeacon> beacons;

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public List<IntelligentBeacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<IntelligentBeacon> beacons) {
        this.beacons = beacons;
    }
}
