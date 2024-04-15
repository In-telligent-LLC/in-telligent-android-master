package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AlertOpenedRequest implements Serializable {
    @SerializedName("notificationId")
    private int notificationId;

    public void setNotificationId(int i) {
        this.notificationId = i;
    }
}
