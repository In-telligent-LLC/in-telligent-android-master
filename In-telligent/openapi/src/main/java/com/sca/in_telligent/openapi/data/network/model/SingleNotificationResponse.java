package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcos Ambrosi on 1/18/19.
 */
public class SingleNotificationResponse {

    @SerializedName("Notification")
    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
