package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class SuggestNotificationRequest implements Serializable {
    @SerializedName("building_id")
    private String buildingId;
    @SerializedName("subscriber_id")
    private String subscriberId;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;
    @SerializedName("attachments")
    private String attachments;

    public void setBuildingId(String str) {
        this.buildingId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setAttachments(ArrayList<String> attachmentPaths) {
    }
}
