package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Marcos Ambrosi on 2/18/19.
 */
public class BeaconsResponse {

    @SerializedName("beaconBuildingPairs")
    private List<BeaconBuildingPair> beaconBuildingPairs;

    public List<BeaconBuildingPair> getBuildingBeaconsPairs() {
        return beaconBuildingPairs;
    }
}
