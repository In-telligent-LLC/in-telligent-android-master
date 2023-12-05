package com.sca.in_telligent.openapi.data.network.model;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Marcos Ambrosi on 1/14/19.
 */
public class SubscribeToCommunityRequest {

    private static final int NO_INVITE_ID = -11;

    public static final String ACTION_SUBSCRIBE = "subscribe";
    public static final String ACTION_UNSUBSCRIBE = "unsubscribe";

    @Retention(SOURCE)
    @StringDef({
            ACTION_SUBSCRIBE,
            ACTION_UNSUBSCRIBE
    })
    public @interface SubscribeAction {
    }

    @SerializedName("buildingId")
    private int communityId;
    @SerializedName("automatic")
    private boolean automatic;
    @SerializedName("inviteId")
    private int inviteId;
    @SerializedName("action")
    private String action;

    public SubscribeToCommunityRequest(int communityId, boolean automatic, int inviteId, @SubscribeAction String action) {
        this.communityId = communityId;
        this.automatic = automatic;
        this.inviteId = inviteId;
        this.action = action;
    }

    public SubscribeToCommunityRequest(int communityId, boolean automatic, @SubscribeAction String action) {
        this.communityId = communityId;
        this.automatic = automatic;
        this.inviteId = NO_INVITE_ID;
        this.action = action;
    }
}
