package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marcos Ambrosi on 4/26/19.
 */
public class AutoSubscribeRequest {

    @SerializedName("buildingId")
    private int communityId;

    @SerializedName("value")
    private boolean optOut;

    public boolean isOptOut() {
        return optOut;
    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
    }

    public AutoSubscribeRequest(int communityId, boolean optOut) {
        this.communityId = communityId;
        this.optOut = optOut;
    }
}
