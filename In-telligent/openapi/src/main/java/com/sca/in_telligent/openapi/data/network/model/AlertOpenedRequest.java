package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlertOpenedRequest implements Serializable {

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @SerializedName("notificationId")
    private int notificationId;

}
