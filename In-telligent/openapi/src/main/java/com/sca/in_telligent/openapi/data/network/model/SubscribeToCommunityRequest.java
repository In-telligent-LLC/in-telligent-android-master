package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.gson.annotations.SerializedName;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SubscribeToCommunityRequest {
    public static final String ACTION_SUBSCRIBE = "subscribe";
    public static final String ACTION_UNSUBSCRIBE = "unsubscribe";
    private static final int NO_INVITE_ID = -11;
    @SerializedName(NativeProtocol.WEB_DIALOG_ACTION)
    private final String action;
    @SerializedName(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC)
    private final boolean automatic;
    @SerializedName("buildingId")
    private final int communityId;
    @SerializedName("inviteId")
    private final int inviteId;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public @interface SubscribeAction {
    }

    public SubscribeToCommunityRequest(int i, boolean z, int i2, String str) {
        this.communityId = i;
        this.automatic = z;
        this.inviteId = i2;
        this.action = str;
    }

    public SubscribeToCommunityRequest(int i, boolean z, String str) {
        this.communityId = i;
        this.automatic = z;
        this.inviteId = NO_INVITE_ID;
        this.action = str;
    }
}
