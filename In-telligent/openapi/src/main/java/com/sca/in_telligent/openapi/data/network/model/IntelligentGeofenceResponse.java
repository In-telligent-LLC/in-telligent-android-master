package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class IntelligentGeofenceResponse implements Serializable {
    @SerializedName("geofences")
    ArrayList<IntelligentGeofence> intelligentGeofences;

    public ArrayList<IntelligentGeofence> getIntelligentGeofences() {
        return this.intelligentGeofences;
    }
}
