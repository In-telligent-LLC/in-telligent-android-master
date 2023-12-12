package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.AnalyticsEvents;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class BuildingsSubscriber implements Serializable {
    @SerializedName("alertsSubscription")
    private String alertsSubscription;
    @SerializedName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC)
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean automatic;
    @SerializedName("offersSubscription")
    private String offersSubscription;

    public String getAlertsSubscription() {
        return this.alertsSubscription;
    }

    public String getOffersSubscription() {
        return this.offersSubscription;
    }

    public boolean isAutomatic() {
        return this.automatic;
    }

    public void setAlertsSubscription(String str) {
        this.alertsSubscription = str;
    }
}
