package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class IntelligentGeofenceResponse implements Serializable {

  public ArrayList<IntelligentGeofence> getIntelligentGeofences() {
    return intelligentGeofences;
  }

  @SerializedName("geofences")
  ArrayList<IntelligentGeofence> intelligentGeofences;
}
