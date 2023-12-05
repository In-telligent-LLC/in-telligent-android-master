package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import org.altbeacon.beacon.Beacon;

public class IntelligentBeacon {

    private static final String TAG = IntelligentBeacon.class.getSimpleName();

    public enum BeaconType {
        @SerializedName("inside")
        INSIDE,
        @SerializedName("outside")
        OUTSIDE,
        @SerializedName("outside")
        LOCATION
    }

    @SerializedName("minorUUID")
    private int minor;

    @SerializedName("beaconGroup")
    private int group;

    @SerializedName("type")
    private BeaconType type;

    @SerializedName("txPower")
    private long txPower;

    @SerializedName("deviceAddress")
    private String deviceAddress;

    @SerializedName("namespaceUUID")
    private String namespaceUuid;

    @SerializedName("instanceId")
    private String instanceId;

    @SerializedName("majorUUID")
    private int major;


    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public BeaconType getType() {
        return type;
    }

    public void setType(BeaconType type) {
        this.type = type;
    }

    public long getTxPower() {
        return txPower;
    }

    public void setTxPower(long txPower) {
        this.txPower = txPower;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getNamespaceUuid() {
        return namespaceUuid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }


    public boolean isBeacon(Beacon beacon) {
        if (this.getMajor() != beacon.getId2().toInt()) {
            return false;
        }

        if (this.getMinor() != beacon.getId3().toInt()) {
            return false;
        }

        return true;
    }
}
