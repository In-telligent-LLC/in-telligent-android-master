package com.sca.in_telligent.openapi.data.network.model;

import android.location.Location;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
//import org.apache.http.cookie.;

public class IntelligentGeofence implements Serializable {
    @SerializedName("id")
    private String id;
    private String idString;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;
    private Location location;
    @SerializedName("name")
    private String name;
    @SerializedName("priority")
    private int priority;
    @SerializedName("radius")
    private int radius;
    @SerializedName("secure")
    private boolean secure;
    @SerializedName("type")
    private String type;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getRadius() {
        return this.radius;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public String getType() {
        return this.type;
    }

    public String getIdString() {
        String str = this.type + "-" + this.id;
        this.idString = str;
        return str;
    }

    public Location getLocation() {
        Location location = new Location("Geofence");
        this.location = location;
        location.setLatitude(this.lat);
        this.location.setLongitude(this.lng);
        return this.location;
    }
}
