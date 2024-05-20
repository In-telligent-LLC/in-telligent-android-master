package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlertDeleteRequest implements Serializable {

    public AlertDeleteRequest() {
        
    }

    public AlertDeleteRequest(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @SerializedName("notificationId")
    private String notificationId;

}
