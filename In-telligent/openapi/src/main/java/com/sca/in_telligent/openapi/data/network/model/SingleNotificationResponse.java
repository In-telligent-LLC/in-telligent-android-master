package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SingleNotificationResponse {
    @SerializedName("Notification")
    private Notification notification;

    public Notification getNotification() {
        return this.notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
