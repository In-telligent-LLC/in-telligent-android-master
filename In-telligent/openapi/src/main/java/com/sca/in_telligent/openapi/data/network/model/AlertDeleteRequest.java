package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AlertDeleteRequest implements Serializable {
    @SerializedName("notificationId")
    private String notificationId;

    public AlertDeleteRequest() {
    }

    public AlertDeleteRequest(String str) {
        this.notificationId = str;
    }

    public void setNotificationId(String str) {
        this.notificationId = str;
    }
}
