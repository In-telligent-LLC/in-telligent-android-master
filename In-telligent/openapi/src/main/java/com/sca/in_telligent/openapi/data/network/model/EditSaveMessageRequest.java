package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.NativeProtocol;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class EditSaveMessageRequest implements Serializable {
    @SerializedName(NativeProtocol.WEB_DIALOG_ACTION)
    private String action;
    @SerializedName("notificationId")
    private String notificationId;

    public void setAction(String str) {
        this.action = str;
    }

    public String getAction() {
        return this.action;
    }

    public void setNotificationId(String str) {
        this.notificationId = str;
    }
}
